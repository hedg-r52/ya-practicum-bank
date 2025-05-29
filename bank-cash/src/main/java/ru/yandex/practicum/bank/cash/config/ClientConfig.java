package ru.yandex.practicum.bank.cash.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.practicum.bank.clients.accounts.AccountClient;

@Configuration
public class ClientConfig {

    @Value("${clients.accounts.uri}")
    private String accountUri;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    public AccountClient accountClient() {
        return new AccountClient(accountUri, webClient());
    }
}

