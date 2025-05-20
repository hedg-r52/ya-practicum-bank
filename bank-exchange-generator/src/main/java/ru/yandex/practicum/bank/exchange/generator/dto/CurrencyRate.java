package ru.yandex.practicum.bank.exchange.generator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyRate {
    private String currency;
    private double rate;
}
