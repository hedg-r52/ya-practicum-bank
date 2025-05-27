package ru.yandex.practicum.bank.front.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.practicum.bank.clients.accounts.AccountClient;

@Configuration
public class ClientsConfig {

    @Value("${gateway_service_url}/accounts")
    private String accountUri;

    @Bean
    public WebClient webClient(ReactiveOAuth2AuthorizedClientManager manager) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth2
                = new ServerOAuth2AuthorizedClientExchangeFilterFunction(manager);

        oauth2.setDefaultClientRegistrationId("gateway");
        return WebClient.builder()
                .filter(oauth2)
                .build();
    }

    @Bean
    public AccountClient accountClient(WebClient webClient) {
        return new AccountClient(accountUri, webClient);
    }
}
