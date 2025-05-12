package ru.yandex.practicum.bank.cash.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.cash.client.AccountWebClient;
import ru.yandex.practicum.bank.cash.dto.CashRequest;
import ru.yandex.practicum.bank.cash.dto.CashResponse;

@Service
public class CashService {

    private final AccountWebClient accountWebClient;

    public CashService(AccountWebClient accountWebClient) {
        this.accountWebClient = accountWebClient;
    }

    public Mono<CashResponse> deposit(CashRequest request) {
        return accountWebClient.deposit(request.getAccountId(), request.getAmount());
    }

    public Mono<CashResponse> withdraw(CashRequest request) {
        return accountWebClient.withdraw(request.getAccountId(), request.getAmount());
    }
}

