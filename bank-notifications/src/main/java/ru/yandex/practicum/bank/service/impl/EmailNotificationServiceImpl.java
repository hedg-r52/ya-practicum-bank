package ru.yandex.practicum.bank.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.client.EmailClient;
import ru.yandex.practicum.bank.dto.EmailNotificationRequestDto;
import ru.yandex.practicum.bank.dto.EmailNotificationResponseDto;
import ru.yandex.practicum.bank.mapper.NotificationMapper;
import ru.yandex.practicum.bank.model.EmailNotification;
import ru.yandex.practicum.bank.repository.EmailNotificationRepository;
import ru.yandex.practicum.bank.service.NotificationService;

@Service
@RequiredArgsConstructor
public class EmailNotificationServiceImpl implements NotificationService {
    private final EmailNotificationRepository emailNotificationRepository;
    private final NotificationMapper notificationMapper;
    private final EmailClient emailClient;


    @Override
    public Mono<EmailNotificationResponseDto> create(EmailNotificationRequestDto request) {
        return Mono.just(request)
                .map(notificationMapper::map)
                .flatMap(emailNotificationRepository::save)
                .map(notificationMapper::map);
    }

    @Scheduled(fixedDelay = 3000)
    @Override
    public Flux<EmailNotification> sendMessages() {
        return emailNotificationRepository.findBySent(false)
                .flatMap(emailClient::sendMessage)
                .flatMap(message -> {
                    message.setSent(true);
                    return emailNotificationRepository.save(message);
                });
    }
}
