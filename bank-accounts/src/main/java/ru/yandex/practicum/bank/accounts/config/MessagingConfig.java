package ru.yandex.practicum.bank.accounts.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import ru.yandex.practicum.bank.messaging.client.NotificationService;
import ru.yandex.practicum.bank.messaging.client.impl.NotificationServiceImpl;
import ru.yandex.practicum.bank.messaging.common.NotificationMessage;

@Configuration
public class MessagingConfig {

    @Bean
    public NotificationService notificationService(KafkaTemplate<String, NotificationMessage> kafkaTemplate,
                                                   MeterRegistry meterRegistry) {
        return new NotificationServiceImpl(kafkaTemplate, meterRegistry);
    }

    @Bean
    public NewTopic notifications() {
        return TopicBuilder.name("notifications")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
