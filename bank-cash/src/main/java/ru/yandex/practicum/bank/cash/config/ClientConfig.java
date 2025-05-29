package ru.yandex.practicum.bank.cash.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.practicum.bank.clients.accounts.AccountClient;
import ru.yandex.practicum.bank.clients.blocker.BlockerClient;
import ru.yandex.practicum.bank.clients.notification.NotificationClient;

@Configuration
public class ClientConfig {

    @Value("${clients.accounts.uri}")
    private String accountUri;

    @Value("${clients.blocker.uri}")
    private String blockerUri;

    @Value("${clients.notifications.uri}")
    private String notificationsUri;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    public AccountClient accountClient() {
        return new AccountClient(accountUri, webClient());
    }

    @Bean
    public BlockerClient blockerClient() {
        return new BlockerClient(blockerUri, webClient());
    }

    @Bean
    public NotificationClient notificationClient() {
        return new NotificationClient(notificationsUri, webClient());
    }
}

