package ru.yandex.practicum.transfer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.transfer.dto.TransactionRequestDto;
import ru.yandex.practicum.transfer.dto.TransactionResponseDto;
import ru.yandex.practicum.transfer.model.TransactionType;
import ru.yandex.practicum.transfer.service.TransactionService;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransactionService transactionService;

    @PostMapping("/{type}")
    public Mono<TransactionResponseDto> create(@PathVariable("type") String transactionType,
                                               @RequestBody @Valid TransactionRequestDto request) {
        var type = TransactionType.valueOf(transactionType.toUpperCase());
        return transactionService.create(type, request);
    }
}
