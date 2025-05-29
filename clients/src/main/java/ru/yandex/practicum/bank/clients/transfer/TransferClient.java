package ru.yandex.practicum.bank.clients.transfer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.clients.AbstractClient;
import ru.yandex.practicum.bank.clients.transfer.dto.TransactionRequest;
import ru.yandex.practicum.bank.clients.transfer.dto.TransactionResponse;
import ru.yandex.practicum.bank.clients.transfer.dto.TransactionType;

@RequiredArgsConstructor
public class TransferClient extends AbstractClient {
    private final String baseUrl;
    private final WebClient webClient;

    public Mono<TransactionResponse> createTransaction(TransactionType type,
                                                       TransactionRequest request) {
        return Mono.just(webClient.post()
                        .uri(baseUrl + "/transfer/" + type.toString().toLowerCase())
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .retrieve())
                .flatMap(responseSpec -> responseToMono(responseSpec, TransactionResponse.class));
    }
}
