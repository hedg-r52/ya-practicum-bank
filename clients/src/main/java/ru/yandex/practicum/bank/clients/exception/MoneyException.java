package ru.yandex.practicum.bank.clients.exception;

public class MoneyException extends RuntimeException {
    public MoneyException(String message) {
        super(message);
    }
}
