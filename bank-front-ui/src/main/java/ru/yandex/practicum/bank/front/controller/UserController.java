package ru.yandex.practicum.bank.front.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.front.dto.user.PasswordChangeRequestDto;
import ru.yandex.practicum.bank.front.dto.user.UserRegisterRequestDto;
import ru.yandex.practicum.bank.front.dto.user.UserUpdateRequestDto;
import ru.yandex.practicum.bank.front.mapper.UserMapper;
import ru.yandex.practicum.bank.front.model.User;
import ru.yandex.practicum.bank.front.service.UserService;
import ru.yandex.practicum.bank.front.utils.SecurityUtils;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/profile")
    public Mono<String> profile(Model model) {
        model.addAttribute(
                "user",
                ReactiveSecurityContextHolder
                        .getContext()
                        .map(SecurityContext::getAuthentication)
                        .switchIfEmpty(Mono.empty())
                        .map(auth -> (User) auth.getPrincipal())
        );
        return Mono.just("profile");
    }

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

    @PutMapping("/user/{userId}")
    public Mono<String> updateUser(@PathVariable Long userId,
                                   @ModelAttribute UserUpdateRequestDto request,
                                   ServerWebExchange exchange) {
        return userService
                .update(userId, request)
                .map(userMapper::map)
                .flatMap(user -> SecurityUtils.updateUserInSession(user, exchange)
                        .then(Mono.just("redirect:/")));
    }

    @PutMapping("/user/{userId}/password")
    public Mono<String> changePassword(@PathVariable Long userId,
                                       @ModelAttribute PasswordChangeRequestDto request,
                                       ServerWebExchange exchange) {
        return userService.changePassword(userId, request)
                .map(userMapper::map)
                .then(Mono.just("redirect:/"));
    }

}
