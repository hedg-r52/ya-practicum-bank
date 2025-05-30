package ru.yandex.practicum.bank.front.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.front.dto.account.AccountCreateRequestDto;
import ru.yandex.practicum.bank.front.dto.account.AccountResponseDto;

import java.util.List;

public interface AccountService {

    Flux<AccountResponseDto> getUserAccounts();

    Flux<AccountResponseDto> getUserAccountsByUserId(Long userId);

    Mono<Void> deleteAccount(Long accountId);

    Mono<AccountResponseDto> createAccount(AccountCreateRequestDto request);

    Mono<List<String>> getCurrencyListNotExistsAtUserAccount();
}
