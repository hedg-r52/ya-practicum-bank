package ru.yandex.practicum.bank.accounts.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.accounts.dto.user.*;

public interface UserService {
    Mono<UserResponseDto> findByLogin(FindByLoginRequestDto request);

    Mono<UserResponseDto> findById(Long userId);

    Mono<UserResponseDto> findByAccountId(Long accountId);

    Mono<UserResponseDto> registerUser(UserRegisterRequestDto request);

    Mono<UserResponseDto> update(Long userId, UserUpdateRequestDto request);

    Mono<UserResponseDto> changePassword(Long userId, PasswordChangeRequestDto request);

    Flux<UserAccountsResponseDto> findAllUsersWithAccounts();
}
