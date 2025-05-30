package ru.yandex.practicum.bank.accounts.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.accounts.dto.account.AccountCreateRequestDto;
import ru.yandex.practicum.bank.accounts.dto.account.AccountResponseDto;
import ru.yandex.practicum.bank.accounts.dto.account.TransferMoneyRequestDto;
import ru.yandex.practicum.bank.accounts.dto.account.TransferMoneyResponseDto;

public interface AccountService {
    Mono<AccountResponseDto> createAccount(AccountCreateRequestDto request);

    Mono<Void> deleteAccount(Long accountId);

    Mono<AccountResponseDto> getAccountById(Long accountId);

    Flux<AccountResponseDto> getUserAccounts(Long userId);

    Mono<AccountResponseDto> depositMoney(Long accountId, Double amount);

    Mono<AccountResponseDto> withdrawMoney(Long accountId, Double amount);

    Mono<TransferMoneyResponseDto> transferMoney(TransferMoneyRequestDto request);
}
