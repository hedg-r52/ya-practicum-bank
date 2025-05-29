package ru.yandex.practicum.bank.blocker.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.blocker.dto.DecisionResponseDto;
import ru.yandex.practicum.bank.blocker.model.Transaction;
import ru.yandex.practicum.bank.blocker.validator.TransactionValidator;
import ru.yandex.practicum.bank.blocker.service.BlockerService;

@Service
public class BlockerServiceImpl implements BlockerService {
    @Override
    public Mono<DecisionResponseDto> verify(Transaction transaction) {
        return TransactionValidator.validate(transaction)
                .map(decision -> DecisionResponseDto.builder()
                        .isBlocked(!decision)
                        .build());
    }
}
