package ru.yandex.practicum.bank.exchange.generator.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.exchange.generator.dto.CurrencyRate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExchangeClient {

    private final WebClient webClient;

    public Mono<Void> sendRates(List<CurrencyRate> rates) {
        return webClient.post()
                .uri("/rates")
                .bodyValue(rates)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
