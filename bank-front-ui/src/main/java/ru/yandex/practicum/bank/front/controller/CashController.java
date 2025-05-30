package ru.yandex.practicum.bank.front.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.front.dto.cash.CashTransactionRequestDto;
import ru.yandex.practicum.bank.front.service.AccountService;
import ru.yandex.practicum.bank.front.service.CashService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cash")
public class CashController {

    private final AccountService accountService;
    private final CashService cashService;

    @GetMapping
    public Mono<String> cashPage(Model model) {
        model.addAttribute("accounts", accountService.getUserAccounts());
        return Mono.just("cash");
    }

    @PostMapping("/deposit")
    public Mono<String> deposit(@ModelAttribute CashTransactionRequestDto request, Model model) {
        return cashService.deposit(request)
                .flatMap(response -> Mono.just("redirect:/cash"));
    }

    @PostMapping("/withdraw")
    public Mono<String> withdraw(@ModelAttribute CashTransactionRequestDto request, Model model) {
        return cashService.withdraw(request)
                .flatMap(response -> Mono.just("redirect:/cash"));
    }

}
