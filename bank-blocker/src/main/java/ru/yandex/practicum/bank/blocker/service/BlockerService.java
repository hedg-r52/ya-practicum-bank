package ru.yandex.practicum.bank.blocker.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.blocker.client.AccountWebClient;
import ru.yandex.practicum.bank.blocker.dto.BlockResponse;
import ru.yandex.practicum.bank.blocker.dto.Operation;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class BlockerService {

    private final AccountWebClient accountWebClient;

    public Mono<BlockResponse> evaluate(final Operation operation) {
        return accountWebClient.getAccountById(operation.getFromAccountId())
                .map(account -> {
                    // For example: blocked if amount greater than 10 000
                    boolean blocked = operation.getAmount().compareTo(BigDecimal.valueOf(10000)) > 0;
                    return new BlockResponse(blocked, "Blocked due to amount:" + operation.getAmount() + " is greater than 10 000");
                });
    }
}
