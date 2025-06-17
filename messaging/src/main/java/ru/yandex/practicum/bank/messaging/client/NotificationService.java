package ru.yandex.practicum.bank.messaging.client;

public interface NotificationService {
    void send(String subject, String message, String email);
}
