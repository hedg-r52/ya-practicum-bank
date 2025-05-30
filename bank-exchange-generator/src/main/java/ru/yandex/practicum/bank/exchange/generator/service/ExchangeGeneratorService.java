package ru.yandex.practicum.bank.exchange.generator.service;

import reactor.core.publisher.Flux;
import ru.yandex.practicum.bank.clients.exchange.dto.ExchangeRateResponse;

public interface ExchangeGeneratorService {

    Flux<ExchangeRateResponse> updateRates();

}
