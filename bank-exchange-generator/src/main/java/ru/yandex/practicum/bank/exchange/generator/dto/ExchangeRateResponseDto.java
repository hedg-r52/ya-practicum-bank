package ru.yandex.practicum.bank.exchange.generator.dto;

import ru.yandex.practicum.bank.exchange.generator.model.Currency;

public class ExchangeRateResponseDto {
    private Currency currency;
    private Double rate;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
