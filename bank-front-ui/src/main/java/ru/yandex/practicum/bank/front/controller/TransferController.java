package ru.yandex.practicum.bank.front.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.front.service.AccountService;
import ru.yandex.practicum.bank.front.service.UserService;
import ru.yandex.practicum.bank.front.utils.SecurityUtils;

@Controller
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransferController {

    private final UserService userService;
    private final AccountService accountService;

    @GetMapping
    public Mono<String> transferPage(Model model) {
        model.addAttribute("accounts", accountService.getUserAccounts());
        model.addAttribute("current", SecurityUtils.getUserId());
        model.addAttribute("users", userService.getUserAccounts());
        return Mono.just("transfer");
    }
}
