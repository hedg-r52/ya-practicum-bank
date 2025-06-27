package ru.yandex.practicum.bank.messaging.exchange;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateUpdateMessageItem {

    private String currency;
    private Double rate;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ExchangeRateUpdateMessageItem{" +
                "currency='" + currency + '\'' +
                ", rate=" + rate +
                '}';
    }
}
