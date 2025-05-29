package ru.yandex.practicum.bank.cash.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.cash.dto.CashTransactionRequestDto;
import ru.yandex.practicum.bank.cash.dto.CashTransactionResponseDto;
import ru.yandex.practicum.bank.cash.mapper.NotificationMapper;
import ru.yandex.practicum.bank.cash.mapper.TransactionMapper;
import ru.yandex.practicum.bank.cash.model.DepositTransaction;
import ru.yandex.practicum.bank.cash.model.Transaction;
import ru.yandex.practicum.bank.cash.model.TransactionStatus;
import ru.yandex.practicum.bank.cash.model.TransactionType;
import ru.yandex.practicum.bank.cash.model.WithdrawTransaction;
import ru.yandex.practicum.bank.cash.repository.TransactionRepository;
import ru.yandex.practicum.bank.cash.service.TransactionService;
import ru.yandex.practicum.bank.clients.accounts.AccountClient;
import ru.yandex.practicum.bank.clients.accounts.dto.accounts.AccountResponse;
import ru.yandex.practicum.bank.clients.accounts.dto.accounts.DepositMoneyToAccount;
import ru.yandex.practicum.bank.clients.accounts.dto.accounts.WithdrawMoneyFromAccount;
import ru.yandex.practicum.bank.clients.accounts.exception.MoneyException;
import ru.yandex.practicum.bank.clients.blocker.BlockerClient;
import ru.yandex.practicum.bank.clients.blocker.dto.DecisionResponse;
import ru.yandex.practicum.bank.clients.notification.NotificationClient;

import java.util.List;

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

    @Override
    public Mono<CashTransactionResponseDto> create(TransactionType type, CashTransactionRequestDto request) {
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
                    log.error("Something Went Wrong during validate transaction process");
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
                        List.of(TransactionStatus.BLOCKED, TransactionStatus.COMPLETED,
                                TransactionStatus.FAILED, TransactionStatus.NOT_ENOUGH_MONEY), false)
                .map(transactionMapper::map)
                .flatMap(this::sendEmail);
    }

    private Mono<DecisionResponse> validate(Transaction transaction) {
        return switch (transaction) {
            case DepositTransaction depositTransaction ->
                    blockerClient.validate(transactionMapper.map(depositTransaction));
            case WithdrawTransaction withdrawalTransaction ->
                    blockerClient.validate(transactionMapper.map(withdrawalTransaction));
            default -> throw new IllegalStateException("Unexpected value: " + transaction);
        };
    }

    private Mono<AccountResponse> processTransaction(Transaction transaction) {
        return switch (transaction) {
            case DepositTransaction depositTransaction -> accountClient.depositMoney(transaction.getAccountId(),
                    DepositMoneyToAccount.builder()
                            .amount(transaction.getAmount())
                            .build());
            case WithdrawTransaction withdrawalTransaction -> accountClient.withdrawMoney(transaction.getAccountId(),
                    WithdrawMoneyFromAccount.builder()
                            .amount(transaction.getAmount())
                            .build());
            default -> throw new IllegalStateException("Unexpected value: " + transaction);
        };
    }

    private Mono<Transaction> sendEmail(Transaction transaction) {
        return accountClient.findUserByAccountId(transaction.getAccountId())
                .map(userResponse -> notificationMapper.map(transaction, userResponse.getEmail()))
                .flatMap(notificationClient::sendEmailNotification)
                .flatMap(response -> {
                    transaction.setNotificationSent(true);
                    return transactionRepository.save(transactionMapper.mapToDb(transaction));
                })
                .map(transactionMapper::map);
    }
}

