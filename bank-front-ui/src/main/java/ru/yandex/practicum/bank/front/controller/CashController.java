package ru.yandex.practicum.bank.front.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.front.service.AccountService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cash")
public class CashController {

    private final AccountService accountService;

    @GetMapping
    public Mono<String> cashPage(Model model) {
        model.addAttribute("accounts", accountService.getUserAccounts());
        return Mono.just("cash");
    }

}
