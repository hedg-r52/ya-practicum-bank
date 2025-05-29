package ru.yandex.practicum.bank.exchange.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.exchange.dto.ExchangeRateRequestDto;
import ru.yandex.practicum.bank.exchange.dto.ExchangeRateResponseDto;
import ru.yandex.practicum.bank.exchange.model.Currency;

import java.util.List;

public interface ExchangeService {
    Flux<ExchangeRateResponseDto> getExchangeRates();

    Mono<ExchangeRateResponseDto> getExchangeRatesByCurrency(Currency currency);

    Flux<ExchangeRateResponseDto> save(List<ExchangeRateRequestDto> exchangeRates);
}
