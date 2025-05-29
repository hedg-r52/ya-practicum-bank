package ru.yandex.practicum.transfer.controller;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public class ErrorResponse {
    private HttpStatus status;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
