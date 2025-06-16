package ru.yandex.practicum.bank.exchange.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bank.exchange.dto.ExchangeRateUpdateRequest;
import ru.yandex.practicum.bank.exchange.service.ExchangeService;
import ru.yandex.practicum.bank.messaging.exchange.ExchangeRateUpdateMessage;

@Service
@RequiredArgsConstructor
public class ExchangeListener {

    private final ExchangeService exchangeService;

    @KafkaListener(
            topics = {"rates"},
            properties = {"spring.json.value.default.type=ru.yandex.practicum.bank.exchange.dto.ExchangeRateUpdateRequest"}
    )
    public void listen(@Payload ExchangeRateUpdateMessage message) {
        exchangeService.save(message.getRates())
                .subscribe();
    }

}
