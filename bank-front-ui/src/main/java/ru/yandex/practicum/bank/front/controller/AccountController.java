package ru.yandex.practicum.bank.front.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.front.service.AccountService;
import ru.yandex.practicum.bank.front.utils.SecurityUtils;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public Mono<String> getUserAccounts(Model model) {
        model.addAttribute("accounts", accountService.getUserAccounts());
        model.addAttribute("userId", SecurityUtils.getUserId());
        model.addAttribute("currencyList", List.of());
        return Mono.just("accounts");
    }

}
