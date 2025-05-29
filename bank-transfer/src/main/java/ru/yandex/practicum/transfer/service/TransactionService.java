package ru.yandex.practicum.transfer.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.transfer.dto.TransactionRequestDto;
import ru.yandex.practicum.transfer.dto.TransactionResponseDto;
import ru.yandex.practicum.transfer.model.Transaction;
import ru.yandex.practicum.transfer.model.TransactionType;

public interface TransactionService {
    Mono<TransactionResponseDto> create(TransactionType transactionType, TransactionRequestDto request);

    Flux<Transaction> validateCreatedTransactions();

    Flux<Transaction> processApprovedTransactions();

    Flux<Transaction> sendNotifications();
}
