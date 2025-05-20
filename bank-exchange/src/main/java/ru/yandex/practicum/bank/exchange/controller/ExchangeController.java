package ru.yandex.practicum.bank.exchange.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.exchange.dto.ExchangeRequest;
import ru.yandex.practicum.bank.exchange.dto.ExchangeResponse;
import ru.yandex.practicum.bank.exchange.service.ExchangeService;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping
    public Mono<ExchangeResponse> convert(@RequestBody ExchangeRequest request) {
        return exchangeService.exchange(request);
    }
}
