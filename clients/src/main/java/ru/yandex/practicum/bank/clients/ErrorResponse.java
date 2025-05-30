package ru.yandex.practicum.bank.clients;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private HttpStatus status;
    private String message;
}
