package ru.yandex.practicum.bank.front.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.yandex.practicum.bank.clients.cash.dto.CashTransactionRequest;
import ru.yandex.practicum.bank.clients.cash.dto.CashTransactionResponse;
import ru.yandex.practicum.bank.front.dto.cash.CashTransactionRequestDto;
import ru.yandex.practicum.bank.front.dto.cash.CashTransactionResponseDto;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CashMapper {
    CashTransactionRequest map(CashTransactionRequestDto request);

    CashTransactionResponseDto map(CashTransactionResponse response);
}
