package ru.yandex.practicum.bank.front.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.front.dto.transfer.TransactionRequestDto;
import ru.yandex.practicum.bank.front.dto.transfer.UserRequest;
import ru.yandex.practicum.bank.front.service.AccountService;
import ru.yandex.practicum.bank.front.service.TransferService;
import ru.yandex.practicum.bank.front.service.UserService;
import ru.yandex.practicum.bank.front.utils.SecurityUtils;

@Controller
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;
    private final UserService userService;
    private final AccountService accountService;

    @GetMapping
    public Mono<String> transferPage(Model model) {
        model.addAttribute("accounts", accountService.getUserAccounts());
        model.addAttribute("current", SecurityUtils.getUserId());
        model.addAttribute("users", userService.getUserAccounts());
        return Mono.just("transfer");
    }

    @PostMapping
    public Mono<String> transferPageWithSelectedUser(@ModelAttribute UserRequest request, Model model) {
        model.addAttribute("accounts", accountService.getUserAccounts());
        model.addAttribute("current", SecurityUtils.getUserId());
        model.addAttribute("users", userService.getUserAccounts());
        model.addAttribute("receiver_id", request.getUserId());
        model.addAttribute("receiver_accounts", accountService.getUserAccountsByUserId(request.getUserId()));
        return Mono.just("transfer");
    }

    @PostMapping("/self")
    public Mono<String> selfTransfer(@ModelAttribute TransactionRequestDto request, Model model) {
        return transferService.selfTransfer(request)
                .flatMap(response -> Mono.just("redirect:/transfer"));
    }

    @PostMapping("/another")
    public Mono<String> anotherTransfer(@ModelAttribute TransactionRequestDto request, Model model) {
        return transferService.transferAnotherUser(request)
                .flatMap(response -> Mono.just("redirect:/transfer"));
    }

}
