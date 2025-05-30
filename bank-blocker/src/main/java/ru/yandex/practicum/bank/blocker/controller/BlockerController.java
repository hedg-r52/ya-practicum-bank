package ru.yandex.practicum.bank.blocker.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.blocker.dto.DecisionResponseDto;
import ru.yandex.practicum.bank.blocker.dto.DepositTransactionRequestDto;
import ru.yandex.practicum.bank.blocker.dto.SelfTransferTransactionRequestDto;
import ru.yandex.practicum.bank.blocker.dto.TransferAnotherUserTransactionRequestDto;
import ru.yandex.practicum.bank.blocker.dto.WithdrawTransactionRequestDto;
import ru.yandex.practicum.bank.blocker.mapper.TransactionMapper;
import ru.yandex.practicum.bank.blocker.service.BlockerService;

@RestController
@RequestMapping("/blocker")
@RequiredArgsConstructor
public class BlockerController {

    private final BlockerService blockerService;
    private final TransactionMapper transactionMapper;

    @PostMapping("/deposit")
    public Mono<DecisionResponseDto> transaction(@RequestBody @Valid DepositTransactionRequestDto transaction) {
        return blockerService.verify(transactionMapper.map(transaction));
    }

    @PostMapping("/withdraw")
    public Mono<DecisionResponseDto> transaction(@RequestBody @Valid WithdrawTransactionRequestDto transaction) {
        return blockerService.verify(transactionMapper.map(transaction));
    }

    @PostMapping("/transfer/self")
    public Mono<DecisionResponseDto> transaction(@RequestBody @Valid SelfTransferTransactionRequestDto transaction) {
        return blockerService.verify(transactionMapper.map(transaction));
    }

    @PostMapping("/transfer/another")
    public Mono<DecisionResponseDto> transaction(@RequestBody @Valid TransferAnotherUserTransactionRequestDto transaction) {
        return blockerService.verify(transactionMapper.map(transaction));
    }
}