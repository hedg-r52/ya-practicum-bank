package ru.yandex.practicum.bank.accounts.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.accounts.dto.account.AccountCreateRequestDto;
import ru.yandex.practicum.bank.accounts.dto.account.AccountResponseDto;
import ru.yandex.practicum.bank.accounts.dto.account.TransferMoneyRequestDto;
import ru.yandex.practicum.bank.accounts.dto.account.TransferMoneyResponseDto;
import ru.yandex.practicum.bank.accounts.exception.DeletionAccountWithFundsException;
import ru.yandex.practicum.bank.accounts.exception.NotEnoughMoneyException;
import ru.yandex.practicum.bank.accounts.exception.NotFoundException;
import ru.yandex.practicum.bank.accounts.mapper.AccountMapper;
import ru.yandex.practicum.bank.accounts.model.Account;
import ru.yandex.practicum.bank.accounts.model.Currency;
import ru.yandex.practicum.bank.accounts.repository.AccountRepository;
import ru.yandex.practicum.bank.accounts.service.AccountService;
import ru.yandex.practicum.bank.accounts.service.UserService;
import ru.yandex.practicum.bank.messaging.client.NotificationService;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserService userService;
    private final NotificationService notificationService;

    @Override
    public Mono<AccountResponseDto> createAccount(AccountCreateRequestDto request) {
        var currency = Currency.valueOf(request.getCurrency());
        var account = new Account(
                request.getUserId(),
                currency
        );
        return accountRepository.save(account)
                .zipWith(userService.findById(request.getUserId()))
                .doOnNext(tuple -> {
                    var acc = tuple.getT1();
                    var user = tuple.getT2();
                    var msg = "Account with id = " + acc.getId()
                            + " currency = " + acc.getCurrency() + " was created";
                    notificationService.send("Создание аккаунта", msg, user.getEmail());
                })
                .map(tuple -> accountMapper.map(tuple.getT1()));
    }

    @Override
    public Mono<Void> deleteAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .switchIfEmpty(Mono.error(new NotFoundException("There is no account with id = " + accountId)))
                .flatMap(account -> {
                    if (account.getAmount() != 0) {
                        return Mono.error(new DeletionAccountWithFundsException());
                    }
                    return userService.findByAccountId(accountId)
                            .flatMap(user -> accountRepository.deleteById(accountId)
                                    .then(Mono.fromRunnable(() -> {
                                        String msg = "Account with id = " + accountId
                                                + " currency = " + account.getCurrency() + " was deleted";
                                        notificationService.send("Удаление аккаунта", msg, user.getEmail());
                                    })));
                });
    }

    @Override
    public Mono<AccountResponseDto> getAccountById(Long accountId) {
        return accountRepository.findById(accountId).map(accountMapper::map);
    }

    @Override
    public Flux<AccountResponseDto> getUserAccounts(Long userId) {
        return accountRepository.findByUserId(userId).map(accountMapper::map);
    }

    @Override
    @Transactional
    public Mono<AccountResponseDto> depositMoney(Long accountId, Double amount) {
        return accountRepository.findById(accountId)
                .switchIfEmpty(Mono.error(new NotFoundException("Account with id = " + accountId + " not found")))
                .flatMap(account -> {
                    account.setAmount(account.getAmount() + amount);
                    return accountRepository.save(account)
                            .zipWith(userService.findById(account.getUserId()));
                })
                .flatMap(tuple -> {
                    var acc = tuple.getT1();
                    var user = tuple.getT2();
                    var msg = amount + " were deposited into account with id = " + acc.getId();
                    notificationService.send("Пополнение счёта", msg, user.getEmail());
                    return Mono.just(acc);
                })
                .map(accountMapper::map);
    }

    @Override
    @Transactional
    public Mono<AccountResponseDto> withdrawMoney(Long accountId, Double amount) {
        return accountRepository.findById(accountId)
                .switchIfEmpty(Mono.error(new NotFoundException("Account with id = " + accountId + " not found")))
                .flatMap(account -> {
                    if (account.getAmount() < amount) {
                        return Mono.error(new NotEnoughMoneyException());
                    }
                    account.setAmount(account.getAmount() - amount);
                    return accountRepository.save(account)
                            .zipWith(userService.findById(account.getUserId()));
                })
                .flatMap(tuple -> {
                    var acc = tuple.getT1();
                    var user = tuple.getT2();
                    var msg = amount + " were withdrew from account with id = " + acc.getId();
                    notificationService.send("Списание со счёта", msg, user.getEmail());
                    return Mono.just(acc);
                })
                .map(accountMapper::map);
    }

    @Override
    @Transactional
    public Mono<TransferMoneyResponseDto> transferMoney(TransferMoneyRequestDto request) {
        return Mono.zip(
                        accountRepository.findById(request.getFromAccountId())
                                .switchIfEmpty(Mono.error(new NotFoundException("Account with id = " + request.getFromAccountId() + " not found"))),
                        accountRepository.findById(request.getToAccountId())
                                .switchIfEmpty(Mono.error(new NotFoundException("Account with id = " + request.getToAccountId() + " not found")))
                )
                .flatMap(tuple -> {
                    Account fromAccount = tuple.getT1();
                    Account toAccount = tuple.getT2();
                    Double fromAmount = request.getFromAmount();
                    Double toAmount = request.getToAmount();

                    if (fromAccount.getAmount() < fromAmount) {
                        return Mono.error(new NotEnoughMoneyException());
                    }

                    fromAccount.setAmount(fromAccount.getAmount() - fromAmount);
                    toAccount.setAmount(toAccount.getAmount() + toAmount);

                    return Mono.zip(
                                    accountRepository.save(fromAccount),
                                    accountRepository.save(toAccount),
                                    userService.findById(fromAccount.getUserId()),
                                    userService.findById(toAccount.getUserId())
                            )
                            .flatMap(saveTuple -> {
                                var savedFromAccount = saveTuple.getT1();
                                var savedToAccount = saveTuple.getT2();
                                var fromUser = saveTuple.getT3();
                                var toUser = saveTuple.getT4();

                                var msgFrom = fromAmount + " were transferred from your account with id = " + savedFromAccount.getId();
                                var msgTo = toAmount + " were deposited into your account with id = " + savedToAccount.getId();

                                notificationService.send("Списание со счёта", msgFrom, fromUser.getEmail());
                                notificationService.send("Пополнение счёта", msgTo, toUser.getEmail());

                                return Mono.empty();
                            })
                            .thenReturn(TransferMoneyResponseDto.builder().completed(true).build());
                });
    }
}
