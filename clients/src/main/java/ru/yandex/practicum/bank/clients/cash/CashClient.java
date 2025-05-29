package ru.yandex.practicum.bank.clients.cash;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.clients.AbstractClient;
import ru.yandex.practicum.bank.clients.cash.dto.CashTransactionRequest;
import ru.yandex.practicum.bank.clients.cash.dto.CashTransactionResponse;
import ru.yandex.practicum.bank.clients.cash.dto.TransactionType;

@RequiredArgsConstructor
public class CashClient extends AbstractClient {
    @Value("${clients.cash.uri}")
    private final String baseUrl;
    private final WebClient webClient;

    public Mono<CashTransactionResponse> createTransaction(TransactionType type,
                                                           CashTransactionRequest request) {
        return Mono.just(webClient
                        .post()
                        .uri(baseUrl + "/transaction/" + type.toString().toLowerCase())
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .retrieve())
                .flatMap(response -> responseToMono(response, CashTransactionResponse.class));
    }
}
