package ru.yandex.practicum.bank.cash.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.cash.dto.CashResponse;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class AccountWebClient {

    private final WebClient webClient;

    public AccountWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<CashResponse> deposit(String accountId, BigDecimal amount) {
        return webClient.post()
                .uri("/accounts/{id}/deposit", accountId)
                .bodyValue(Map.of("amount", amount))
                .retrieve()
                .bodyToMono(CashResponse.class);
    }

    public Mono<CashResponse> withdraw(String accountId, BigDecimal amount) {
        return webClient.post()
                .uri("/accounts/{id}/withdraw", accountId)
                .bodyValue(Map.of("amount", amount))
                .retrieve()
                .bodyToMono(CashResponse.class);
    }
}

