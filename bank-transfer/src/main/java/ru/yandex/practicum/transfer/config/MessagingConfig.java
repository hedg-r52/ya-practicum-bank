package ru.yandex.practicum.transfer.config;

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
    public NotificationService notificationService(ApplicationContext ctx, KafkaTemplate<String, NotificationMessage> kafkaTemplate) {
        return new NotificationServiceImpl(ctx, kafkaTemplate);
    }

    @Bean
    public NewTopic notifications() {
        return TopicBuilder.name("notifications")
                .partitions(1)
                .replicas(1)
                .build();
    }

}
