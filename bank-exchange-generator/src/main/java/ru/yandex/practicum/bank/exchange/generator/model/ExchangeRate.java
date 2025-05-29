package ru.yandex.practicum.bank.exchange.generator.model;

import lombok.Builder;

@Builder
public class ExchangeRate {
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
