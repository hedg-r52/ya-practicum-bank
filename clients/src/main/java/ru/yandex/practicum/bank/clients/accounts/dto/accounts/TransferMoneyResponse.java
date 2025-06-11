package ru.yandex.practicum.bank.clients.accounts.dto.accounts;

public class TransferMoneyResponse {
    public TransferMoneyResponse() {}

    private boolean completed;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
