package ru.yandex.practicum.transfer.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.transfer.client.AccountWebClient;
import ru.yandex.practicum.transfer.dto.TransferRequest;

@Service
public class TransferService {

    private final AccountWebClient accountWebClient;

    public TransferService(AccountWebClient accountWebClient) {
        this.accountWebClient = accountWebClient;
    }

    public Mono<Void> transfer(TransferRequest transferRequest) {
        return accountWebClient.transfer(transferRequest);
    }
}
