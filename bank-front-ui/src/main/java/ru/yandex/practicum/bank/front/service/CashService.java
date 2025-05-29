package ru.yandex.practicum.bank.front.service;

import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.front.dto.cash.CashTransactionRequestDto;
import ru.yandex.practicum.bank.front.dto.cash.CashTransactionResponseDto;

public interface CashService {
    Mono<CashTransactionResponseDto> deposit(CashTransactionRequestDto request);

    Mono<CashTransactionResponseDto> withdraw(CashTransactionRequestDto request);
}
