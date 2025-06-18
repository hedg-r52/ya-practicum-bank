package ru.yandex.practicum.bank.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.dto.EmailNotificationResponseDto;
import ru.yandex.practicum.bank.mapper.NotificationMapper;
import ru.yandex.practicum.bank.messaging.common.NotificationMessage;
import ru.yandex.practicum.bank.service.NotificationService;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationKafkaConsumer {

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    @RetryableTopic(backoff = @Backoff(500), timeout = "5000")
    @KafkaListener(topics = "notifications", groupId = "notifications-group")
    public Mono<EmailNotificationResponseDto> listen(NotificationMessage notificationMessage) {
        return notificationService.create(notificationMapper.map(notificationMessage));
    }
}
