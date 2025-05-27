package ru.yandex.practicum.bank.accounts.exception;

public class DeletionAccountWithFundsException extends RuntimeException {
    public DeletionAccountWithFundsException() {
        super("Deletion account with some funds was canceled");
    }
}
