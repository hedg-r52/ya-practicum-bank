package ru.yandex.practicum.bank.front.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.clients.cash.CashClient;
import ru.yandex.practicum.bank.clients.cash.dto.TransactionType;
import ru.yandex.practicum.bank.front.dto.cash.CashTransactionRequestDto;
import ru.yandex.practicum.bank.front.dto.cash.CashTransactionResponseDto;
import ru.yandex.practicum.bank.front.mapper.CashMapper;
import ru.yandex.practicum.bank.front.service.CashService;

@Log4j2
@Service
@RequiredArgsConstructor
public class CashServiceImpl implements CashService {

    private final CashClient cashClient;
    private final CashMapper cashMapper;

    @Override
    public Mono<CashTransactionResponseDto> deposit(CashTransactionRequestDto request) {
        return cashClient.createTransaction(TransactionType.DEPOSIT, cashMapper.map(request))
                .doOnError(throwable -> log.error("Error creating deposit transaction: {}", throwable.getMessage()))
                .map(cashMapper::map);
    }

    @Override
    public Mono<CashTransactionResponseDto> withdraw(CashTransactionRequestDto request) {
        return cashClient.createTransaction(TransactionType.WITHDRAW, cashMapper.map(request))
                .doOnError(throwable -> log.error("Error creating withdraw transaction: {}", throwable.getMessage()))
                .map(cashMapper::map);
    }
}
