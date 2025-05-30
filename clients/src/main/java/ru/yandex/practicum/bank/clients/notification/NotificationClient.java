package ru.yandex.practicum.bank.clients.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.clients.AbstractClient;
import ru.yandex.practicum.bank.clients.notification.dto.EmailNotificationRequest;
import ru.yandex.practicum.bank.clients.notification.dto.EmailNotificationResponse;

@RequiredArgsConstructor
public class NotificationClient extends AbstractClient {
    private final String baseUrl;
    private final WebClient webClient;

    public Mono<EmailNotificationResponse> sendEmailNotification(EmailNotificationRequest request) {
        return Mono.just(webClient
                        .post()
                        .uri(baseUrl + "/notifications/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .retrieve())
                .flatMap(response -> responseToMono(response, EmailNotificationResponse.class));
    }
}
