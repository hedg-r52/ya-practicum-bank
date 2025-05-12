package ru.yandex.practicum.bank.cash.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.cash.dto.CashRequest;
import ru.yandex.practicum.bank.cash.dto.CashResponse;
import ru.yandex.practicum.bank.cash.service.CashService;

@RestController
@RequestMapping("/cash")
public class CashController {

    private final CashService cashService;

    public CashController(CashService cashService) {
        this.cashService = cashService;
    }

    @PostMapping("/deposit")
    public Mono<ResponseEntity<CashResponse>> deposit(@RequestBody CashRequest request) {
        return cashService.deposit(request)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/withdraw")
    public Mono<ResponseEntity<CashResponse>> withdraw(@RequestBody CashRequest request) {
        return cashService.withdraw(request)
                .map(ResponseEntity::ok);
    }
}
