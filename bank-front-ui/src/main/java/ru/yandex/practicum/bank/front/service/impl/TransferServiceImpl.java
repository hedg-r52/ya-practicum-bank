package ru.yandex.practicum.bank.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.clients.transfer.TransferClient;
import ru.yandex.practicum.bank.clients.transfer.dto.TransactionResponse;
import ru.yandex.practicum.bank.clients.transfer.dto.TransactionType;
import ru.yandex.practicum.bank.front.dto.transfer.TransactionRequestDto;
import ru.yandex.practicum.bank.front.mapper.TransferMapper;
import ru.yandex.practicum.bank.front.service.TransferService;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransferClient transferClient;
    private final TransferMapper transferMapper;

    @Override
    public Mono<TransactionResponse> selfTransfer(TransactionRequestDto request) {
        return transferClient.createTransaction(TransactionType.SELF_TRANSFER, transferMapper.map(request));
    }

    @Override
    public Mono<TransactionResponse> transferAnotherUser(TransactionRequestDto request) {
        return transferClient.createTransaction(TransactionType.TRANSFER_TO_ANOTHER_USER, transferMapper.map(request));
    }

}
