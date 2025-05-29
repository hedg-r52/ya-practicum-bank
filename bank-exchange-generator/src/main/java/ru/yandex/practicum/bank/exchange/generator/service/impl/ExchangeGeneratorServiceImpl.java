package ru.yandex.practicum.bank.exchange.generator.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.clients.exchange.ExchangeClient;
import ru.yandex.practicum.bank.clients.exchange.dto.ExchangeRateResponse;
import ru.yandex.practicum.bank.exchange.generator.mapper.ExchangeMapper;
import ru.yandex.practicum.bank.exchange.generator.model.Currency;
import ru.yandex.practicum.bank.exchange.generator.model.ExchangeRate;
import ru.yandex.practicum.bank.exchange.generator.service.ExchangeGeneratorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeGeneratorServiceImpl implements ExchangeGeneratorService {

    private final ExchangeMapper exchangeMapper;
    private final ExchangeClient exchangeClient;

    @Scheduled(fixedDelay = 1000)
    @Override
    public Flux<ExchangeRateResponse> updateRates() {
        return generateRates()
                .flatMapMany(Flux::fromIterable)
                .map(exchangeMapper::map)
                .collectList()
                .flatMapMany(exchangeClient::update);
    }

    private Mono<List<ExchangeRate>> generateRates() {
        return Flux.just(Currency.values())
                .map(currency -> ExchangeRate.builder()
                        .currency(currency)
                        .rate(generateRateForCurrency(currency))
                        .build())
                .collectList();
    }

    private double generateRateForCurrency(Currency currency) {
        return switch (currency) {
            case RUB -> 1.0;
            case USD -> getRandomNumber(0.0115, 0.013);
            case EUR -> getRandomNumber(0.0105, 0.0115);
        };
    }

    public double getRandomNumber(double min, double max) {
        return ((Math.random() * (max - min)) + min);
    }
}
