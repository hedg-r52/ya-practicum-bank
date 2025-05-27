package ru.yandex.practicum.bank.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.clients.accounts.AccountClient;
import ru.yandex.practicum.bank.clients.accounts.dto.accounts.AccountResponse;
import ru.yandex.practicum.bank.front.dto.account.AccountCreateRequestDto;
import ru.yandex.practicum.bank.front.dto.account.AccountResponseDto;
import ru.yandex.practicum.bank.front.mapper.AccountMapper;
import ru.yandex.practicum.bank.front.service.AccountService;
import ru.yandex.practicum.bank.front.utils.SecurityUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountClient accountClient;
    private final AccountMapper accountMapper;

    private final List<String> defaultCurrencies = List.of("RUB", "USD", "EUR");

    @Override
    public Flux<AccountResponseDto> getUserAccounts() {
        return SecurityUtils.getUserId()
                .flatMapMany(accountClient::getUserAccounts)
                .map(accountMapper::map);
    }

    @Override
    public Flux<AccountResponseDto> getUserAccountsByUserId(Long userId) {
        return accountClient.getUserAccounts(userId)
                .map(accountMapper::map);
    }

    @Override
    public Mono<Void> deleteAccount(Long accountId) {
        return accountClient.deleteAccount(accountId);
    }

    @Override
    public Mono<AccountResponseDto> createAccount(AccountCreateRequestDto request) {
        return accountClient.createAccount(accountMapper.map(request))
                .map(accountMapper::map);
    }

    @Override
    public Mono<List<String>> getCurrencyListNotExistsAtUserAccount() {
        return getUserAccounts()
                .map(AccountResponseDto::getCurrency)
                .collectList()
                .map(existList -> defaultCurrencies.stream()
                        .filter(i -> !existList.contains(i))
                        .toList()
                );
    }
}
