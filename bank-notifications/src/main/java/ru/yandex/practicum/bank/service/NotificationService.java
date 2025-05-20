package ru.yandex.practicum.bank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.dto.Notification;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender mailSender;

    public Mono<Void> sendEmail(Notification notification) {
        return Mono.fromRunnable(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(notification.getTo());
            message.setSubject(notification.getSubject());
            message.setText(notification.getMessage());
            mailSender.send(message);
        });
    }

    public Mono<Void> sendSms(Notification notification) {
        // TODO: Реализация через сторонний SMS API
        return Mono.fromRunnable(() -> {
            System.out.println("Sending SMS to " + notification.getTo() + ": " + notification.getMessage());
        });
    }

    public Mono<Void> sendPush(Notification notification) {
        // TODO: Реализация push-уведомлений
        return Mono.fromRunnable(() -> {
            System.out.println("Sending Push to " + notification.getTo() + ": " + notification.getMessage());
        });
    }
}

