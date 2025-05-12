package ru.yandex.practicum.transfer.client;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.transfer.dto.TransferRequest;

@Component
public class AccountWebClient {

    private final WebClient webClient;

    public AccountWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Void> transfer(TransferRequest transferRequest) {
        return webClient.post()
                .uri("/accounts/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transferRequest)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
