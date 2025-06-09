package ru.yandex.practicum.bank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.dto.EmailNotificationRequestDto;
import ru.yandex.practicum.bank.dto.EmailNotificationResponseDto;
import ru.yandex.practicum.bank.service.NotificationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class EmailNotificationController {
    private final NotificationService notificationService;

    @PostMapping("/email")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EmailNotificationResponseDto> save(@RequestBody EmailNotificationRequestDto request) {
        return notificationService.create(request);
    }
}
