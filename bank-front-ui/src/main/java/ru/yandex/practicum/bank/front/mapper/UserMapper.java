package ru.yandex.practicum.bank.front.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.yandex.practicum.bank.clients.accounts.dto.accounts.UserAccountsResponse;
import ru.yandex.practicum.bank.clients.accounts.dto.user.PasswordChangeRequest;
import ru.yandex.practicum.bank.clients.accounts.dto.user.UserRegisterRequest;
import ru.yandex.practicum.bank.clients.accounts.dto.user.UserResponse;
import ru.yandex.practicum.bank.clients.accounts.dto.user.UserUpdateRequest;
import ru.yandex.practicum.bank.front.dto.user.*;
import ru.yandex.practicum.bank.front.model.User;

@Mapper(
        uses = {AccountMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {
    public abstract User mapToUser(UserResponse response);

    public abstract UserUpdateRequest map(UserUpdateRequestDto request);

    public abstract UserAccountsResponseDto map(UserAccountsResponse response);

    public abstract UserResponseDto map(UserResponse response);

    public abstract User map(UserResponseDto response);

    public abstract UserRegisterRequest map(UserRegisterRequestDto request);

    public abstract PasswordChangeRequest map(PasswordChangeRequestDto request);
}
