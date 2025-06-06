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

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public Mono<AccountResponseDto> createAccount(AccountCreateRequestDto request) {
        var currency = Currency.valueOf(request.getCurrency());
        var account = new Account(
                request.getUserId(),
                currency
        );
        return accountRepository.save(account)
                .onErrorResume(Mono::error)
                .map(accountMapper::map);
    }

    @Override
    public Mono<Void> deleteAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .switchIfEmpty(Mono.error(new NotFoundException("There is no account with id = " + accountId)))
                .doOnNext(a -> {
                    if (a.getAmount() != 0) {
                        throw new DeletionAccountWithFundsException();
                    }
                })
                .flatMap(a -> accountRepository.deleteById(accountId));
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
                .doOnNext(a -> a.setAmount(a.getAmount() + amount))
                .flatMap(accountRepository::save)
                .map(accountMapper::map);
    }

    @Override
    @Transactional
    public Mono<AccountResponseDto> withdrawMoney(Long accountId, Double amount) {
        return accountRepository.findById(accountId)
                .doOnNext(a -> {
                    if (a.getAmount() < amount) {
                        throw new NotEnoughMoneyException();
                    }
                    a.setAmount(a.getAmount() - amount);
                })
                .flatMap(accountRepository::save)
                .map(accountMapper::map);
    }

    @Override
    @Transactional
    public Mono<TransferMoneyResponseDto> transferMoney(TransferMoneyRequestDto request) {
        return Mono.zip(
                        accountRepository.findById(request.getFromAccountId()),
                        accountRepository.findById(request.getToAccountId())
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

                    return accountRepository.save(fromAccount)
                            .then(accountRepository.save(toAccount));
                })
                .thenReturn(TransferMoneyResponseDto.builder().completed(true).build());
    }
}
