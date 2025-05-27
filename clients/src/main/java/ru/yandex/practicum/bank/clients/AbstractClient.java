package ru.yandex.practicum.bank.clients;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.clients.exception.BadRequestException;
import ru.yandex.practicum.bank.clients.exception.ErrorResponse;
import ru.yandex.practicum.bank.clients.exception.InternalServerRequestException;

public abstract class AbstractClient {
    protected <T> Mono<T> responseToMono(WebClient.ResponseSpec responseSpec, Class<T> responseClass) {
        return processErrors(responseSpec)
                .bodyToMono(responseClass);
    }

    protected <T> Flux<T> responseToFlux(WebClient.ResponseSpec responseSpec, Class<T> responseClass) {
        return processErrors(responseSpec)
                .bodyToFlux(responseClass);
    }

    private WebClient.ResponseSpec processErrors(WebClient.ResponseSpec responseSpec) {
        return responseSpec
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(ErrorResponse.class)
                                .map(errorResponse ->
                                        new BadRequestException(
                                                response.request().getURI().toString(),
                                                errorResponse.getMessage()
                                        )
                                )
                )
                .onStatus(
                        HttpStatus.INTERNAL_SERVER_ERROR::equals,
                        response -> response.bodyToMono(String.class)
                                .map(errorResponse ->
                                        new InternalServerRequestException(
                                                response.request().getURI().toString(),
                                                errorResponse
                                        )
                                )
                );
    }
}
