package ru.yandex.practicum.bank.messaging.exchange;

import java.util.List;

public class ExchangeRateUpdateMessage {
    List<ExchangeRateUpdateMessageItem> rates;

    public List<ExchangeRateUpdateMessageItem> getRates() {
        return rates;
    }

    public void setRates(List<ExchangeRateUpdateMessageItem> rates) {
        this.rates = rates;
    }
}
