package ru.yandex.practicum.bank.blocker.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.blocker.dto.DecisionResponseDto;
import ru.yandex.practicum.bank.blocker.model.Transaction;
import ru.yandex.practicum.bank.blocker.validator.TransactionValidator;
import ru.yandex.practicum.bank.blocker.service.BlockerService;

@Log4j2
@Service
public class BlockerServiceImpl implements BlockerService {
    @Override
    public Mono<DecisionResponseDto> verify(Transaction transaction) {
        log.debug("Verifying transaction {}", transaction);
        return TransactionValidator.validate(transaction)
                .map(decision -> DecisionResponseDto.builder()
                        .isBlocked(!decision)
                        .build())
                .doOnNext(decision ->
                        log.debug("Transaction ({}) is validated, decision: {}", transaction, decision)
                );
    }
}
