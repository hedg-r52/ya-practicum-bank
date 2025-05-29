package ru.yandex.practicum.bank.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.dto.EmailNotificationRequestDto;
import ru.yandex.practicum.bank.dto.EmailNotificationResponseDto;
import ru.yandex.practicum.bank.model.EmailNotification;

public interface NotificationService {
    Mono<EmailNotificationResponseDto> create(EmailNotificationRequestDto request);

    Flux<EmailNotification> sendMessages();
}

