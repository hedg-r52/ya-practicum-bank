package ru.yandex.practicum.bank.front.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.front.dto.user.*;

public interface UserService {

    Mono<UserResponseDto> register(UserRegisterRequestDto request);

    Mono<UserResponseDto> update(Long userId, UserUpdateRequestDto request);

    Mono<UserResponseDto> changePassword(Long userId, PasswordChangeRequestDto request);

    Flux<UserAccountsResponseDto> getUserAccounts();
}
