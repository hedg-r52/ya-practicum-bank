package ru.yandex.practicum.bank.accounts.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.accounts.dto.user.*;
import ru.yandex.practicum.bank.accounts.exception.UserNotFoundException;
import ru.yandex.practicum.bank.accounts.mapper.AccountMapper;
import ru.yandex.practicum.bank.accounts.mapper.UserMapper;
import ru.yandex.practicum.bank.accounts.model.Account;
import ru.yandex.practicum.bank.accounts.repository.AccountRepository;
import ru.yandex.practicum.bank.accounts.repository.UserRepository;
import ru.yandex.practicum.bank.accounts.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public Mono<UserResponseDto> findByLogin(FindByLoginRequestDto request) {
        return userRepository.findByLogin(request.getLogin())
                .switchIfEmpty(Mono.error(() -> {
                    log.debug("User not found by login: {}", request.getLogin());
                    return new UserNotFoundException();
                }))
                .map(userMapper::map);
    }

    @Override
    public Mono<UserResponseDto> findById(Long userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(() -> {
                    log.debug("User not found by id: {}", userId);
                    return new UserNotFoundException();
                }))
                .map(userMapper::map);
    }

    @Override
    public Mono<UserResponseDto> findByAccountId(Long accountId) {
        return userRepository.findByAccountId(accountId)
                .switchIfEmpty(Mono.error(() -> {
                    log.debug("User not found by accountId: {}", accountId);
                    return new UserNotFoundException();
                }))
                .map(userMapper::map);
    }

    @Override
    public Mono<UserResponseDto> registerUser(UserRegisterRequestDto request) {
        log.debug("Registering user: {}", request);
        return userRepository.save(userMapper.map(request))
                .doOnError(throwable -> log.error("Error registering user: {}", throwable.getMessage()))
                .map(userMapper::map)
                .doOnNext(userResponseDto -> log.info("User successfully registered: {}", userResponseDto));
    }

    @Override
    public Mono<UserResponseDto> update(Long userId, UserUpdateRequestDto request) {
        log.debug("Updating user: {}", userId);
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(() -> {
                    log.debug("User not found by id: {}", userId);
                    return new UserNotFoundException(userId);
                }))
                .map(u -> userMapper.update(request, u))
                .flatMap(userRepository::save)
                .doOnError(throwable -> log.error("Error updating user: {}", throwable.getMessage()))
                .map(userMapper::map)
                .doOnNext(userResponseDto -> log.info("User successfully updated: {}", userResponseDto));
    }

    @Override
    public Mono<UserResponseDto> changePassword(Long userId, PasswordChangeRequestDto request) {
        log.debug("Changing password for user: {}", userId);
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(() -> {
                    log.error("User not found by id: {}", userId);
                    return new UserNotFoundException(userId);
                }))
                .doOnNext(u -> u.setPassword(passwordEncoder.encode(request.getPassword())))
                .flatMap(userRepository::save)
                .doOnError(throwable -> log.error("Error changing password for user: {}", throwable.getMessage()))
                .map(userMapper::map)
                .doOnNext(userResponseDto -> log.info("User successfully changed: {}", userResponseDto));
    }

    @Override
    public Flux<UserAccountsResponseDto> findAllUsersWithAccounts() {
        return userRepository.findAll()
                .map(userMapper::mapUserAccounts)
                .collectList()
                .zipWith(accountRepository.findAll().collectList())
                .flatMapMany(tuple -> {
                    var users = tuple.getT1();
                    var accounts = tuple.getT2();
                    return Flux.fromIterable(collectUserAccounts(users, accounts));
                })
                .filter(u -> u.getAccounts() != null);
    }

    private Collection<UserAccountsResponseDto> collectUserAccounts(List<UserAccountsResponseDto> users,
                                                                    List<Account> accounts) {
        var usersMap = new HashMap<Long, UserAccountsResponseDto>();
        users.forEach(u -> usersMap.put(u.getId(), u));
        accounts.forEach(a -> {
            var user = usersMap.get(a.getUserId());
            if (user.getAccounts() == null) {
                user.setAccounts(new ArrayList<>());
            }
            user.getAccounts().add(accountMapper.map(a));
        });
        return usersMap.values();
    }
}
