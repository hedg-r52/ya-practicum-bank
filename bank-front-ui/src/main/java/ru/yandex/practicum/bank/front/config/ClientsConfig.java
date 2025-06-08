package ru.yandex.practicum.bank.front.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.clients.accounts.AccountClient;
import ru.yandex.practicum.bank.clients.cash.CashClient;
import ru.yandex.practicum.bank.clients.exchange.ExchangeClient;
import ru.yandex.practicum.bank.clients.transfer.TransferClient;

@Configuration
public class ClientsConfig {

    @Value("${clients.accounts.uri}")
    private String accountUri;

    @Value("${clients.exchange.uri}")
    private String exchangeUri;

    @Value("${clients.cash.uri}")
    private String cashUri;

    @Value("${clients.transfer.uri}")
    private String transferUri;

    @Bean
    public WebClient webClient(ReactiveOAuth2AuthorizedClientManager manager) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth2
                = new ServerOAuth2AuthorizedClientExchangeFilterFunction(manager);

        oauth2.setDefaultClientRegistrationId("keycloak");
        return WebClient.builder()
                .filter(logRequest())    // лог запроса
                .filter(logResponse())   // лог ответа
                .filter(oauth2)
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            System.out.println("➡️  Request: " + clientRequest.method() + " " + clientRequest.url());
            clientRequest.headers().forEach((name, values) ->
                    values.forEach(value -> System.out.println("   " + name + ": " + value)));
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            System.out.println("⬅️  Response status: " + clientResponse.statusCode());
            clientResponse.headers().asHttpHeaders().forEach((name, values) ->
                    values.forEach(value -> System.out.println("   " + name + ": " + value)));
            return Mono.just(clientResponse);
        });
    }

    @Bean
    public AccountClient accountClient(WebClient webClient) {
        return new AccountClient(accountUri, webClient);
    }

    @Bean
    public ExchangeClient exchangeClient(WebClient webClient) {
        return new ExchangeClient(exchangeUri, webClient);
    }

    @Bean
    public CashClient cashClient(WebClient webClient) {
        return new CashClient(cashUri, webClient);
    }

    @Bean
    public TransferClient transferClient(WebClient webClient) {
        return new TransferClient(transferUri, webClient);
    }
}
