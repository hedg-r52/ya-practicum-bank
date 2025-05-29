package ru.yandex.practicum.bank.blocker.service;

import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.blocker.dto.DecisionResponseDto;
import ru.yandex.practicum.bank.blocker.model.Transaction;

public interface BlockerService {
    Mono<DecisionResponseDto> verify(Transaction transaction);
}
