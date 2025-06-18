package ru.yandex.practicum.bank.exchange.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.exchange.dto.ExchangeRateResponseDto;
import ru.yandex.practicum.bank.exchange.model.Currency;
import ru.yandex.practicum.bank.exchange.service.ExchangeService;


@RestController
@RequestMapping("/api/exchange/rates")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping
    public Flux<ExchangeRateResponseDto> getRates() {
        return exchangeService.getExchangeRates();
    }

    @GetMapping("/{currency}")
    public Mono<ExchangeRateResponseDto> getByCurrency(@PathVariable String currency) {
        return exchangeService.getExchangeRatesByCurrency(Currency.valueOf(currency));
    }

}
