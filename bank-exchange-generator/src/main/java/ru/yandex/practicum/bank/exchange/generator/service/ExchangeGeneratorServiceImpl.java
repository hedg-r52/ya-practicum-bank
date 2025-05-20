package ru.yandex.practicum.bank.exchange.generator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bank.exchange.generator.client.ExchangeClient;
import ru.yandex.practicum.bank.exchange.generator.dto.CurrencyRate;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ExchangeGeneratorServiceImpl implements ExchangeGeneratorService {

    private final ExchangeClient exchangeClient;
    private final Random random = new Random();

    @Override
    @Scheduled(fixedRate = 1000)
    public void generateAndSendRates() {
        List<CurrencyRate> rates = List.of(
                new CurrencyRate("USD", randomRate()),
                new CurrencyRate("CNY", randomRate()),
                new CurrencyRate("RUB", 1.0)
        );

        exchangeClient.sendRates(rates)
                .subscribe();
    }

    private double randomRate() {
        return 0.5 + (2.0 - 0.5) * random.nextDouble();
    }
}
