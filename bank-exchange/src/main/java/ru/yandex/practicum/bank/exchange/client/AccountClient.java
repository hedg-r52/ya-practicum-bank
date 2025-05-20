package ru.yandex.practicum.bank.exchange.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountClient {

    private final WebClient webClient;

    public Mono<Void> withdraw(UUID accountId, BigDecimal amount) {
        return webClient.post()
                .uri("http://bank-accounts/accounts/{id}/withdraw", accountId)
                .bodyValue(amount)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Void> deposit(UUID accountId, BigDecimal amount) {
        return webClient.post()
                .uri("http://bank-accounts/accounts/{id}/deposit", accountId)
                .bodyValue(amount)
                .retrieve()
                .bodyToMono(Void.class);
    }

}
