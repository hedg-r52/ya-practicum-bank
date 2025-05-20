package ru.yandex.practicum.bank.exchange.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ExchangeRateClient {

    private final WebClient webClient;

    public Mono<BigDecimal> getRate(String from, String to) {
        return webClient.get()
                .uri("http://bank-exchange-generator/rates/{from}/{to}", from, to)
                .retrieve()
                .bodyToMono(BigDecimal.class);
    }

}
