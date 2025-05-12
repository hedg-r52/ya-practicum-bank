package ru.yandex.practicum.transfer.dto;

import java.math.BigDecimal;

public record TransferRequest(
        String fromAccountId,
        String toAccountId,
        BigDecimal amount
) {}
