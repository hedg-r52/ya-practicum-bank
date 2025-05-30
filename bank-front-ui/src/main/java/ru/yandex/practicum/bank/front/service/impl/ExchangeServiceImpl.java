package ru.yandex.practicum.bank.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.yandex.practicum.bank.clients.exchange.ExchangeClient;
import ru.yandex.practicum.bank.front.dto.exchange.ExchangeRateResponseDto;
import ru.yandex.practicum.bank.front.mapper.ExchangeMapper;
import ru.yandex.practicum.bank.front.service.ExchangeService;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeClient exchangeClient;
    private final ExchangeMapper exchangeMapper;

    @Override
    public Flux<ExchangeRateResponseDto> readRates() {
        return exchangeClient.getAll()
                .map(exchangeMapper::map);
    }
}
