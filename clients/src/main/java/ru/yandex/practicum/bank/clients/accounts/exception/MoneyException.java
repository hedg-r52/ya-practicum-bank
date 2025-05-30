package ru.yandex.practicum.bank.clients.accounts.exception;

public class MoneyException extends RuntimeException {
    public MoneyException(String message) {
        super(message);
    }
}
