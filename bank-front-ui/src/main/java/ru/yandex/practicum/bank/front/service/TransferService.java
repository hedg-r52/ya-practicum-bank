package ru.yandex.practicum.bank.front.service;

import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.clients.transfer.dto.TransactionResponse;
import ru.yandex.practicum.bank.front.dto.transfer.TransactionRequestDto;

public interface TransferService {
    Mono<TransactionResponse> selfTransfer(TransactionRequestDto request);

    Mono<TransactionResponse> transferAnotherUser(TransactionRequestDto request);
}
