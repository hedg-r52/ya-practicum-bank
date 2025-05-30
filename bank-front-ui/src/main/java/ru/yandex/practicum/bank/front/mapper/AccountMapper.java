package ru.yandex.practicum.bank.front.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.yandex.practicum.bank.clients.accounts.dto.accounts.AccountCreateRequest;
import ru.yandex.practicum.bank.clients.accounts.dto.accounts.AccountResponse;
import ru.yandex.practicum.bank.front.dto.account.AccountCreateRequestDto;
import ru.yandex.practicum.bank.front.dto.account.AccountResponseDto;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AccountMapper {
    AccountCreateRequest map(AccountCreateRequestDto request);

    AccountResponseDto map(AccountResponse response);
}
