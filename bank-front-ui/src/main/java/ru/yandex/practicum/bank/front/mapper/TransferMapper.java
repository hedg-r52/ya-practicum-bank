package ru.yandex.practicum.bank.front.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.yandex.practicum.bank.clients.transfer.dto.TransactionRequest;
import ru.yandex.practicum.bank.clients.transfer.dto.TransactionResponse;
import ru.yandex.practicum.bank.front.dto.transfer.TransactionRequestDto;
import ru.yandex.practicum.bank.front.dto.transfer.TransactionResponseDto;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TransferMapper {
    TransactionRequest map(TransactionRequestDto request);

    TransactionResponseDto map(TransactionResponse response);
}
