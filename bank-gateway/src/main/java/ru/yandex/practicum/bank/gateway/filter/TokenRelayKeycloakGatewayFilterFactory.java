package ru.yandex.practicum.bank.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

@Component
public class TokenRelayKeycloakGatewayFilterFactory extends AbstractGatewayFilterFactory<TokenRelayKeycloakGatewayFilterFactory.Config> {

    private final ReactiveOAuth2AuthorizedClientManager manager;

    public TokenRelayKeycloakGatewayFilterFactory(ReactiveOAuth2AuthorizedClientManager manager) {
        super(Config.class);
        this.manager = manager;
    }

    @Override
    public GatewayFilter apply(TokenRelayKeycloakGatewayFilterFactory.Config config) {
        OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
                .withClientRegistrationId(config.getRegistrationId())
                .principal("NA")
                .build();

        return ((exchange, chain) ->
                manager.authorize(request)
                        .flatMap(client -> chain.filter(
                                exchange.mutate()
                                        .request(
                                                exchange.getRequest().mutate().header(
                                                        "Authorization",
                                                        "Bearer " + client.getAccessToken().getTokenValue()).build()
                                        )
                                        .build())));
    }

    public static class Config {
        private String registrationId;

        public String getRegistrationId() {
            return registrationId;
        }

        public void setRegistrationId(String registrationId) {
            this.registrationId = registrationId;
        }
    }
}
