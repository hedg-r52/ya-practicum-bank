package ru.yandex.practicum.bank.accounts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.accounts.dto.user.FindByLoginRequestDto;
import ru.yandex.practicum.bank.accounts.dto.user.UserRegisterRequestDto;
import ru.yandex.practicum.bank.accounts.dto.user.UserResponseDto;
import ru.yandex.practicum.bank.accounts.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public Mono<String> register() {
        return Mono.just("<h1>Test</h1>");
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponseDto> registerUser(@RequestBody UserRegisterRequestDto request) {
        return userService.registerUser(request);
    }

    @PostMapping("/find/login")
    public Mono<UserResponseDto> findByLogin(@RequestBody FindByLoginRequestDto request) {
        return userService.findByLogin(request);
    }

}
