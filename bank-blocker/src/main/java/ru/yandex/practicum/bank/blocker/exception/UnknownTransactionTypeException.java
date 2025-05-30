package ru.yandex.practicum.bank.blocker.exception;

public class UnknownTransactionTypeException extends RuntimeException {
    public UnknownTransactionTypeException(String message) {
        super("Unknown transaction type: " + message);
    }
}
