package ru.yandex.practicum.bank.clients.accounts.exception;

public class SignupException extends RuntimeException {
    public SignupException(String message) {
        super(message);
    }
}
