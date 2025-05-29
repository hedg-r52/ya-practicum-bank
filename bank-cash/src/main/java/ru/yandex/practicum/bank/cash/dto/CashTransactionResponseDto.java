package ru.yandex.practicum.bank.cash.dto;


import ru.yandex.practicum.bank.cash.model.TransactionStatus;
import ru.yandex.practicum.bank.cash.model.TransactionType;

import java.time.LocalDateTime;

public class CashTransactionResponseDto {
    private Long id;
    private Long accountId;
    private Double amount;
    private TransactionType type;
    private TransactionStatus status;
    private LocalDateTime createdAt;
}
