package ru.yandex.practicum.bank.blocker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    private String operationId;
    private String fromAccountId;
    private String toAccountId;
    private BigDecimal amount;
    private Instant timestamp;
}
