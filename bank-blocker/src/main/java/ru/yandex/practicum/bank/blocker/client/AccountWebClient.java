package ru.yandex.practicum.bank.blocker.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AccountWebClient {

    private final WebClient webClient;

    public Mono<String> getAccountById(String accountId) {
        return webClient.get().
                uri("/accounts/{accountId}", accountId)
                .retrieve()
                .bodyToMono(String.class);
    }
}
