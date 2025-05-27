package ru.yandex.practicum.bank.clients.exception;

import org.springframework.http.HttpStatus;

public class InternalServerRequestException extends RuntimeException {
    public InternalServerRequestException(String uri, String message) {
        super(
                String.format(
                        "Error request %s (Status: %d - %s",
                        uri,
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        message
                )
        );
    }
}
