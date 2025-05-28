package ru.yandex.practicum.bank.front.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.front.dto.account.AccountCreateRequestDto;
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
        model.addAttribute("currencyList", accountService.getCurrencyListNotExistsAtUserAccount());
        return Mono.just("accounts");
    }

    @PostMapping("/accounts")
    public Mono<String> createAccount(@ModelAttribute AccountCreateRequestDto request) {
        return accountService.createAccount(request)
                .then(Mono.just("redirect:/"));
    }

    @DeleteMapping("/accounts/{accountId}")
    public Mono<String> deleteAccount(@PathVariable Long accountId) {
        return accountService.deleteAccount(accountId)
                .then(Mono.just("redirect:/"));
    }
}
