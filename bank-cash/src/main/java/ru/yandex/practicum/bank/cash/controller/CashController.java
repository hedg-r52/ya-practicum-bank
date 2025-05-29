package ru.yandex.practicum.bank.cash.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.cash.dto.CashTransactionRequestDto;
import ru.yandex.practicum.bank.cash.dto.CashTransactionResponseDto;
import ru.yandex.practicum.bank.cash.service.impl.TransactionServiceImpl;
import ru.yandex.practicum.bank.clients.cash.dto.TransactionType;

@RestController
@RequestMapping("/cash")
public class CashController {

    private final TransactionServiceImpl transactionService;

    public CashController(TransactionServiceImpl transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/{type}")
    public Mono<CashTransactionResponseDto> create(@PathVariable String type,
                                                   @RequestBody CashTransactionRequestDto request) {
        return transactionService.create(
                TransactionType.valueOf(type.toUpperCase()),
                request
        );
    }
}
