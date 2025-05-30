package ru.yandex.practicum.bank.front.service;

import reactor.core.publisher.Flux;
import ru.yandex.practicum.bank.front.dto.exchange.ExchangeRateResponseDto;

public interface ExchangeService {
    Flux<ExchangeRateResponseDto> readRates();
}
