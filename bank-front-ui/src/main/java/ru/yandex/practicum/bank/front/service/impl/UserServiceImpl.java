package ru.yandex.practicum.bank.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.clients.accounts.AccountClient;
import ru.yandex.practicum.bank.clients.accounts.dto.user.FindByLoginRequest;
import ru.yandex.practicum.bank.front.dto.user.*;
import ru.yandex.practicum.bank.front.mapper.UserMapper;
import ru.yandex.practicum.bank.front.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, ReactiveUserDetailsService {
    private final AccountClient accountClient;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(@Value("${gateway_service_url}/accounts") String accountClientUrl,
                           WebClient webClient,
                           UserMapper userMapper) {
        this.accountClient = new AccountClient(accountClientUrl, webClient);
        this.userMapper = userMapper;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return accountClient
                .findUserByLogin(
                        FindByLoginRequest.builder()
                                .login(username)
                                .build()
                )
                .map(userMapper::mapToUser);
    }

    @Override
    public Mono<UserResponseDto> register(UserRegisterRequestDto request) {
        return accountClient
                .registerUser(userMapper.map(request))
                .map(userMapper::map);
    }

    @Override
    public Mono<UserResponseDto> update(Long userId, UserUpdateRequestDto request) {
        return accountClient.updateUser(userId, userMapper.map(request))
                .map(userMapper::map);
    }

    @Override
    public Mono<UserResponseDto> changePassword(Long userId, PasswordChangeRequestDto request) {
        return accountClient.changePassword(userId, userMapper.map(request))
                .map(userMapper::map);
    }

    @Override
    public Flux<UserAccountsResponseDto> getUserAccounts() {
        return accountClient.getUserAccounts()
                .map(userMapper::map);
    }
}
