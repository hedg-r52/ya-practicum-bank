package ru.yandex.practicum.transfer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.practicum.bank.clients.accounts.AccountClient;
import ru.yandex.practicum.bank.clients.blocker.BlockerClient;
import ru.yandex.practicum.bank.clients.exchange.ExchangeClient;
import ru.yandex.practicum.bank.clients.notification.NotificationClient;

@Configuration
public class ClientConfig {
    @Value("${clients.accounts.uri}")
    private String accountsUri;
    @Value("${clients.blocker.uri}")
    String blockerUri;
    @Value("${clients.notifications.uri}")
    String notificationsUri;
    @Value("${clients.exchange.uri}")
    String exchangeUri;


    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .build();
    }

    @Bean
    public AccountClient accountsClient() {
        return new AccountClient(accountsUri, webClient());
    }

    @Bean
    public BlockerClient blockerClient() {
        return new BlockerClient(blockerUri, webClient());
    }

    @Bean
    public NotificationClient notificationClient() {
        return new NotificationClient(notificationsUri, webClient());
    }

    @Bean
    public ExchangeClient exchangeClient() {
        return new ExchangeClient(exchangeUri, webClient());
    }
}
