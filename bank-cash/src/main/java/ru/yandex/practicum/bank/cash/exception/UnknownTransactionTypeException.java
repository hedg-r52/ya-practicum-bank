package ru.yandex.practicum.bank.cash.exception;

public class UnknownTransactionTypeException extends RuntimeException {
    public UnknownTransactionTypeException() {
        super("Unknown transaction type");
    }
}
