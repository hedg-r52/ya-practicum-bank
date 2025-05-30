package ru.yandex.practicum.bank.clients.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String uri, String message) {
        super(
                String.format(
                        "Error request %s (Status: %d - %s",
                        uri,
                        HttpStatus.BAD_REQUEST.value(),
                        message
                )
        );
    }
}
