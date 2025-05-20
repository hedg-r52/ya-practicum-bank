package ru.yandex.practicum.bank.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRequest {
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal amount;
    private UUID accountId;
}
