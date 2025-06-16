package ru.yandex.practicum.bank.exchange.generator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.clients.exchange.ExchangeClient;
import ru.yandex.practicum.bank.clients.exchange.dto.ExchangeRateResponse;
import ru.yandex.practicum.bank.exchange.generator.dto.ExchangeRateUpdateRequest;
import ru.yandex.practicum.bank.exchange.generator.mapper.ExchangeMapper;
import ru.yandex.practicum.bank.exchange.generator.model.Currency;
import ru.yandex.practicum.bank.exchange.generator.model.ExchangeRate;
import ru.yandex.practicum.bank.messaging.exchange.ExchangeRateUpdateMessage;
import ru.yandex.practicum.bank.messaging.exchange.ExchangeRateUpdateMessageItem;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProduceMessageScheduler {

    private final KafkaTemplate<String, ExchangeRateUpdateMessage> kafkaTemplate;
    private final ExchangeMapper exchangeMapper;

    @Scheduled(fixedDelay = 1000)
    public Mono<Void> updateRates() {
        return generateRates()
                .flatMapMany(Flux::fromIterable)
                .collectList()
                .doOnNext(exchangeRates -> {
                    var rates = new ExchangeRateUpdateMessage();
                    List<ExchangeRateUpdateMessageItem> items = new ArrayList<>();
                    exchangeRates.forEach(rate -> items.add(new ExchangeRateUpdateMessageItem(
                            rate.getCurrency().toString(),
                            rate.getRate()
                    )));
                    rates.setRates(items);
                    kafkaTemplate.send("rates", rates);
                })
                .then();
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
