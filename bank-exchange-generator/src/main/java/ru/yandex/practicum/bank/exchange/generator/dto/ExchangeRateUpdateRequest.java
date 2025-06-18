package ru.yandex.practicum.bank.exchange.generator.dto;

import ru.yandex.practicum.bank.exchange.generator.model.ExchangeRate;

import java.util.List;

public class ExchangeRateUpdateRequest {
    public List<ExchangeRate> getRates() {
        return rates;
    }

    public void setRates(List<ExchangeRate> rates) {
        this.rates = rates;
    }

    List<ExchangeRate> rates;
}
