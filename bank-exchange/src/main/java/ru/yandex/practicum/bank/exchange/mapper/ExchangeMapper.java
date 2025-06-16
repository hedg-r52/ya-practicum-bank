package ru.yandex.practicum.bank.exchange.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.yandex.practicum.bank.exchange.dto.ExchangeRateResponseDto;
import ru.yandex.practicum.bank.exchange.dto.ExchangeRateUpdateRequest;
import ru.yandex.practicum.bank.exchange.model.ExchangeRate;
import ru.yandex.practicum.bank.messaging.exchange.ExchangeRateUpdateMessageItem;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ExchangeMapper {
    ExchangeRateResponseDto map(ExchangeRate exchangeRate);

    ExchangeRate map(ExchangeRateUpdateRequest request);
    ExchangeRate map(ExchangeRateUpdateMessageItem request);
}
