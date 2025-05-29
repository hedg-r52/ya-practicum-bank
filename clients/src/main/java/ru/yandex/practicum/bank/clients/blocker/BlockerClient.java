package ru.yandex.practicum.bank.clients.blocker;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.clients.AbstractClient;
import ru.yandex.practicum.bank.clients.blocker.dto.DecisionResponse;
import ru.yandex.practicum.bank.clients.blocker.dto.DepositTransactionRequest;
import ru.yandex.practicum.bank.clients.blocker.dto.SelfTransferTransactionRequest;
import ru.yandex.practicum.bank.clients.blocker.dto.TransferAnotherUserTransactionRequest;
import ru.yandex.practicum.bank.clients.blocker.dto.WithdrawTransactionRequest;

@RequiredArgsConstructor
public class BlockerClient extends AbstractClient {
    private final String baseUrl;
    private final WebClient webClient;

    public Mono<DecisionResponse> validate(DepositTransactionRequest request) {
        return Mono.just(webClient
                        .post()
                        .uri(baseUrl + "/blocker/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .retrieve())
                .flatMap(response -> responseToMono(response, DecisionResponse.class));
    }

    public Mono<DecisionResponse> validate(WithdrawTransactionRequest request) {
        return Mono.just(webClient
                        .post()
                        .uri(baseUrl + "/blocker/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .retrieve())
                .flatMap(response -> responseToMono(response, DecisionResponse.class));
    }

    public Mono<DecisionResponse> validate(SelfTransferTransactionRequest request) {
        return Mono.just(webClient
                        .post()
                        .uri(baseUrl + "/blocker/transfer/self")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .retrieve())
                .flatMap(response -> responseToMono(response, DecisionResponse.class));
    }

    public Mono<DecisionResponse> validate(TransferAnotherUserTransactionRequest request) {
        return Mono.just(webClient
                        .post()
                        .uri(baseUrl + "/blocker/transfer/another")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .retrieve())
                .flatMap(response -> responseToMono(response, DecisionResponse.class));
    }
}
