package ru.yandex.practicum.bank.exchange.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.exchange.client.AccountClient;
import ru.yandex.practicum.bank.exchange.client.ExchangeRateClient;
import ru.yandex.practicum.bank.exchange.dto.ExchangeRequest;
import ru.yandex.practicum.bank.exchange.dto.ExchangeResponse;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeRateClient rateClient;
    private final AccountClient accountClient;

    public Mono<ExchangeResponse> exchange(ExchangeRequest request) {
        return rateClient.getRate(request.getFromCurrency(), request.getToCurrency())
                .flatMap(rate -> {
                    BigDecimal convertedAmount = request.getAmount().multiply(rate);

                    return accountClient.withdraw(request.getAccountId(), request.getAmount())
                            .then(accountClient.deposit(request.getAccountId(), convertedAmount))
                            .thenReturn(new ExchangeResponse(
                                    request.getFromCurrency(),
                                    request.getToCurrency(),
                                    request.getAmount(),
                                    convertedAmount,
                                    rate
                            ));
                });
    }

}
