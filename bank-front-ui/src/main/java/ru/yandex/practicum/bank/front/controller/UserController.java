package ru.yandex.practicum.bank.front.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.front.dto.user.FindByLoginRequestDto;
import ru.yandex.practicum.bank.front.dto.user.UserRegisterRequestDto;
import ru.yandex.practicum.bank.front.dto.user.UserResponseDto;
import ru.yandex.practicum.bank.front.mapper.UserMapper;
import ru.yandex.practicum.bank.front.service.UserService;
import ru.yandex.practicum.bank.front.utils.SecurityUtils;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/signup")
    public Mono<String> signup(Model model, ServerWebExchange exchange) {
        return Mono.just("signup");
    }

    @PostMapping("/signup")
    public Mono<String> signup(@ModelAttribute UserRegisterRequestDto requestDto, ServerWebExchange exchange) {
        return userService
                .register(requestDto)
                .map(userMapper::map)
                .flatMap(user -> SecurityUtils.updateUserInSession(user, exchange)
                        .then(Mono.just("redirect:/login")));
    }

}
