package ru.yandex.practicum.bank.accounts.exception;

public class NotificationSendException extends RuntimeException {
    public NotificationSendException(String message) {
        super(message);
    }
}
