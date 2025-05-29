package ru.yandex.practicum.bank.cash.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.cash.dto.CashTransactionRequestDto;
import ru.yandex.practicum.bank.cash.dto.CashTransactionResponseDto;
import ru.yandex.practicum.bank.cash.model.Transaction;
import ru.yandex.practicum.bank.cash.model.TransactionType;

public interface TransactionService {
    Mono<CashTransactionResponseDto> create(TransactionType type, CashTransactionRequestDto request);

    Flux<Transaction> validateCreatedTransactions();

    Flux<Transaction> processApprovedTransactions();

    Flux<Transaction> sendNotifications();
}
