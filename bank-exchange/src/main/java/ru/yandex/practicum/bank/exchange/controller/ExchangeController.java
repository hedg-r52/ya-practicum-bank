package ru.yandex.practicum.bank.exchange.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.exchange.dto.ExchangeRateRequestDto;
import ru.yandex.practicum.bank.exchange.dto.ExchangeRateResponseDto;
import ru.yandex.practicum.bank.exchange.model.Currency;
import ru.yandex.practicum.bank.exchange.service.ExchangeService;

import java.util.List;

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

    @PostMapping
    public Flux<ExchangeRateResponseDto> save(@RequestBody List<ExchangeRateRequestDto> request) {
        return exchangeService.save(request);
    }
}
