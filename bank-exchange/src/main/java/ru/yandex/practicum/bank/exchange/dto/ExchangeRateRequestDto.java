package ru.yandex.practicum.bank.exchange.dto;

import ru.yandex.practicum.bank.exchange.model.Currency;

public class ExchangeRateRequestDto {
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
