package ru.yandex.practicum.bank.messaging.client.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bank.messaging.client.NotificationService;
import ru.yandex.practicum.bank.messaging.common.NotificationMessage;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final ApplicationContext applicationContext;
    private final KafkaTemplate<String, NotificationMessage> kafkaTemplate;

    public NotificationServiceImpl(ApplicationContext applicationContext, KafkaTemplate<String, NotificationMessage> kafkaTemplate) {
        this.applicationContext = applicationContext;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${kafka.topic:notifications}")
    private String kafkaTopic;

    @Value("${kafka.timeout.seconds:10}")
    private Integer kafkaTimeoutSeconds;

    @Override
    public void send(String subject, String message, String email) {
        var notification = new NotificationMessage();
        notification.setSubject(subject);
        notification.setMessage(message);
        notification.setRecipient(email);
        log.info("Sending notification: {}", notification);
        var guid = UUID.randomUUID().toString();
        Message<NotificationMessage> msg = MessageBuilder
                .withPayload(notification)
                .setHeader(KafkaHeaders.TOPIC, kafkaTopic)
                .setHeader(KafkaHeaders.KEY, guid)
                .setHeader("idempotencyKey", guid)
                .build();
        try {
            kafkaTemplate.send(msg).get(kafkaTimeoutSeconds, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            log.error("{}:{}", e.getMessage(), e.getCause().getMessage());
            return;
        }
        log.info("Notification sent: {}", notification);
    }
}
