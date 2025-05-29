package ru.yandex.practicum.transfer.exception;

public class UnknownTransactionTypeException extends RuntimeException {
    public UnknownTransactionTypeException(String name) {
        super("Unknown transaction type: " + name);
    }
}
