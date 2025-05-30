package ru.yandex.practicum.bank.clients.exchange;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.clients.AbstractClient;
import ru.yandex.practicum.bank.clients.exchange.dto.Currency;
import ru.yandex.practicum.bank.clients.exchange.dto.ExchangeRateRequest;
import ru.yandex.practicum.bank.clients.exchange.dto.ExchangeRateResponse;

import java.util.List;

@RequiredArgsConstructor
public class ExchangeClient extends AbstractClient {
    private final String baseUrl;
    private final WebClient webClient;

    public Flux<ExchangeRateResponse> getAll() {
        return Flux.just(webClient
                        .get()
                        .uri(baseUrl + "/exchange/rates")
                        .retrieve())
                .flatMap(response -> responseToFlux(response, ExchangeRateResponse.class));
    }

    public Mono<ExchangeRateResponse> getByCurrency(Currency currency) {
        return Mono.just(webClient
                        .get()
                        .uri(baseUrl + "/exchange/rates/" + currency)
                        .retrieve())
                .flatMap(response -> responseToMono(response, ExchangeRateResponse.class));
    }

    public Flux<ExchangeRateResponse> update(List<ExchangeRateRequest> request) {
        return Flux.just(webClient
                        .post()
                        .uri(baseUrl + "/exchange/rates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .retrieve())
                .flatMap(response -> responseToFlux(response, ExchangeRateResponse.class));
    }
}
