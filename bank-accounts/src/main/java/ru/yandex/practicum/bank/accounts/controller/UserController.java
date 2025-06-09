package ru.yandex.practicum.bank.accounts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.accounts.dto.user.FindByLoginRequestDto;
import ru.yandex.practicum.bank.accounts.dto.user.PasswordChangeRequestDto;
import ru.yandex.practicum.bank.accounts.dto.user.UserAccountsResponseDto;
import ru.yandex.practicum.bank.accounts.dto.user.UserRegisterRequestDto;
import ru.yandex.practicum.bank.accounts.dto.user.UserResponseDto;
import ru.yandex.practicum.bank.accounts.dto.user.UserUpdateRequestDto;
import ru.yandex.practicum.bank.accounts.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponseDto> registerUser(@RequestBody UserRegisterRequestDto request) {
        return userService.registerUser(request);
    }

    @PostMapping("/find/login")
    public Mono<UserResponseDto> findByLogin(@RequestBody FindByLoginRequestDto request) {
        return userService.findByLogin(request);
    }

    @GetMapping("/find/account/{accountId}")
    public Mono<UserResponseDto> findByAccountId(@PathVariable Long accountId) {
        return userService.findByAccountId(accountId);
    }

    @GetMapping("/accounts")
    public Flux<UserAccountsResponseDto> findAllUsersWithAccounts() {
        return userService.findAllUsersWithAccounts();
    }

    @PutMapping("/{userId}")
    public Mono<UserResponseDto> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequestDto request) {
        return userService.update(userId, request);
    }

    @PutMapping("/{userId}/password")
    public Mono<UserResponseDto> changePassword(@PathVariable Long userId, @RequestBody PasswordChangeRequestDto request) {
        return userService.changePassword(userId, request);
    }

}
