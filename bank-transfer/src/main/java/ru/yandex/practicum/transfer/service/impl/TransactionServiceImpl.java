package ru.yandex.practicum.transfer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.clients.accounts.AccountClient;
import ru.yandex.practicum.bank.clients.accounts.dto.accounts.TransferMoneyRequest;
import ru.yandex.practicum.bank.clients.accounts.dto.accounts.TransferMoneyResponse;
import ru.yandex.practicum.bank.clients.accounts.exception.MoneyException;
import ru.yandex.practicum.bank.clients.blocker.BlockerClient;
import ru.yandex.practicum.bank.clients.blocker.dto.DecisionResponse;
import ru.yandex.practicum.bank.clients.exchange.ExchangeClient;
import ru.yandex.practicum.bank.clients.exchange.dto.Currency;
import ru.yandex.practicum.bank.clients.exchange.dto.ExchangeRateResponse;
import ru.yandex.practicum.bank.clients.notification.NotificationClient;
import ru.yandex.practicum.transfer.dto.TransactionRequestDto;
import ru.yandex.practicum.transfer.dto.TransactionResponseDto;
import ru.yandex.practicum.transfer.exception.UnknownTransactionTypeException;
import ru.yandex.practicum.transfer.mapper.NotificationMapper;
import ru.yandex.practicum.transfer.mapper.TransactionMapper;
import ru.yandex.practicum.transfer.model.SelfTransferTransaction;
import ru.yandex.practicum.transfer.model.Transaction;
import ru.yandex.practicum.transfer.model.TransactionStatus;
import ru.yandex.practicum.transfer.model.TransactionType;
import ru.yandex.practicum.transfer.model.TransferAnotherUserTransaction;
import ru.yandex.practicum.transfer.repository.TransactionRepository;
import ru.yandex.practicum.transfer.service.TransactionService;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountClient accountClient;
    private final BlockerClient blockerClient;
    private final NotificationClient notificationClient;
    private final NotificationMapper notificationMapper;
    private final ExchangeClient exchangeClient;

    @Override
    public Mono<TransactionResponseDto> create(TransactionType type, TransactionRequestDto request) {
        return Mono.just(transactionMapper.map(type, request))
                .map(transactionMapper::mapToDb)
                .flatMap(transactionRepository::save)
                .map(transactionMapper::map)
                .map(transactionMapper::map);
    }

    @Scheduled(fixedDelay = 3000)
    @Override
    public Flux<Transaction> validateCreatedTransactions() {
        return transactionRepository
                .findByStatus(TransactionStatus.CREATED)
                .map(transactionMapper::map)
                .flatMap(transaction -> validate(transaction)
                        .flatMap(decisionResponse -> {
                            transaction.setStatus(decisionResponse.isBlocked() ?
                                    TransactionStatus.BLOCKED :
                                    TransactionStatus.APPROVED);
                            return transactionRepository.save(transactionMapper.mapToDb(transaction));
                        }))
                .map(transactionMapper::map)
                .onErrorResume(e -> {
                    log.error("Something Went Wrong");
                    return Mono.empty();
                });
    }

    @Scheduled(fixedDelay = 3000)
    @Override
    public Flux<Transaction> processApprovedTransactions() {
        return transactionRepository
                .findByStatus(TransactionStatus.APPROVED)
                .map(transactionMapper::map)
                .flatMap(transaction -> processTransaction(transaction)
                        .onErrorResume(e -> {
                            if (e instanceof MoneyException) {
                                transaction.setStatus(TransactionStatus.NOT_ENOUGH_MONEY);
                            } else {
                                transaction.setStatus(TransactionStatus.FAILED);
                            }

                            return transactionRepository.save(transactionMapper.mapToDb(transaction))
                                    .flatMap(transactionRepositoryDto -> Mono.error(e));
                        })
                        .flatMap(account -> {
                            transaction.setStatus(TransactionStatus.COMPLETED);
                            return transactionRepository.save(transactionMapper.mapToDb(transaction));
                        })
                )
                .map(transactionMapper::map);
    }

    @Scheduled(fixedDelay = 3000)
    @Override
    public Flux<Transaction> sendNotifications() {
        return transactionRepository.findByStatusInAndNotificationSent(
                        List.of(TransactionStatus.BLOCKED, TransactionStatus.COMPLETED, TransactionStatus.FAILED,
                                TransactionStatus.NOT_ENOUGH_MONEY), false)
                .map(transactionMapper::map)
                .flatMap(this::sendEmail);
    }

    private Mono<DecisionResponse> validate(Transaction transaction) {
        return switch (transaction) {
            case SelfTransferTransaction selfTransferTransaction ->
                    blockerClient.validate(transactionMapper.map(selfTransferTransaction));
            case TransferAnotherUserTransaction transferToOtherUserTransaction ->
                    blockerClient.validate(transactionMapper.map(transferToOtherUserTransaction));
            default -> throw new UnknownTransactionTypeException("Unexpected value: "
                    + transaction.getClass().getSimpleName());
        };
    }

    private Mono<TransferMoneyResponse> processTransaction(Transaction transaction) {
        return exchangeClient.getAll()
                .collectMap(ExchangeRateResponse::getCurrency, ExchangeRateResponse::getRate)
                .zipWith(readCurrenciesOfAccounts(transaction))
                .flatMap(tuple -> {
                    var rates = tuple.getT1();
                    var currencies = tuple.getT2();
                    return accountClient.transferMoney(TransferMoneyRequest.builder()
                            .fromAccountId(transaction.getAccountId())
                            .fromAmount(transaction.getAmount())
                            .toAccountId(transaction.getReceiverAccountId())
                            .toAmount(exchangeMoney(transaction.getAmount(), currencies.getFirst(), currencies.getLast(), rates))
                            .build());
                });
    }

    private Mono<List<Currency>> readCurrenciesOfAccounts(Transaction transaction) {
        return accountClient.getAccountById(transaction.getAccountId())
                .zipWith(accountClient.getAccountById(transaction.getReceiverAccountId()))
                .map(tuple ->
                        List.of(Currency.valueOf(tuple.getT1().getCurrency()), Currency.valueOf(tuple.getT2().getCurrency())));
    }

    private Double exchangeMoney(Double amount, Currency from, Currency to, Map<Currency, Double> rates) {
        Double amountInRubles = amount / rates.get(from);
        return amountInRubles * rates.get(to);
    }

    private Mono<Transaction> sendEmail(Transaction transaction) {
        return accountClient.findUserByAccountId(transaction.getAccountId())
                .map(userResponse -> notificationMapper.map(transaction, userResponse.getEmail(),
                        userResponse.getLastName() + " " + userResponse.getFirstName()))
                .flatMap(notificationClient::sendEmailNotification)
                .flatMap(emailNotificationResponse -> {
                    transaction.setNotificationSent(true);
                    return transactionRepository.save(transactionMapper.mapToDb(transaction));
                })
                .map(transactionMapper::map);
    }
}
