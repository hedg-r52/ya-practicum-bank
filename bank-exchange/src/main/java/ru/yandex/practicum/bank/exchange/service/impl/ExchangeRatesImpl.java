package ru.yandex.practicum.bank.exchange.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.exchange.dto.ExchangeRateResponseDto;
import ru.yandex.practicum.bank.exchange.mapper.ExchangeMapper;
import ru.yandex.practicum.bank.exchange.model.Currency;
import ru.yandex.practicum.bank.exchange.model.ExchangeRate;
import ru.yandex.practicum.bank.exchange.repository.ExchangeRepository;
import ru.yandex.practicum.bank.exchange.service.ExchangeService;
import ru.yandex.practicum.bank.messaging.exchange.ExchangeRateUpdateMessageItem;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ExchangeRatesImpl implements ExchangeService {

    private final ExchangeMapper exchangeMapper;
    private final ExchangeRepository exchangeRepository;

    @Override
    public Flux<ExchangeRateResponseDto> getExchangeRates() {
        return exchangeRepository.findAll()
                .map(exchangeMapper::map);
    }

    @Override
    public Mono<ExchangeRateResponseDto> getExchangeRatesByCurrency(Currency currency) {
        return exchangeRepository.findByCurrency(currency)
                .map(exchangeMapper::map);
    }

    @Override
    public Flux<ExchangeRateResponseDto> save(List<ExchangeRateUpdateMessageItem> exchangeRates) {
        log.debug("exchange rates update request received: {}", exchangeRates);
        return Flux.fromIterable(exchangeRates)
                .map(exchangeMapper::map)
                .collectList()
                .flatMap(this::enrichRatesByIds)
                .flatMapMany(exchangeRepository::saveAll)
                .doOnError(throwable -> log.error("Error appears during saving exchange rates: {}", throwable.getMessage()))
                .map(exchangeMapper::map)
                .doOnNext(exchangeRateResponseDto -> log.info("Exchange rates saved to db: {}", exchangeRateResponseDto));

    }

    private Mono<List<ExchangeRate>> enrichRatesByIds(List<ExchangeRate> exchangeRates) {
        return exchangeRepository.findAll()
                .collectMap(ExchangeRate::getCurrency, rate -> rate)
                .map(rateMap -> {
                    exchangeRates.forEach(exchangeRate -> {
                        if (rateMap.containsKey(exchangeRate.getCurrency())) {
                            ExchangeRate existingRate = rateMap.get(exchangeRate.getCurrency());
                            exchangeRate.setId(existingRate.getId());
                            exchangeRate.setCreatedAt(existingRate.getCreatedAt());
                            exchangeRate.setModifiedAt(existingRate.getModifiedAt());
                        }
                    });
                    return exchangeRates;
                });
    }
}
