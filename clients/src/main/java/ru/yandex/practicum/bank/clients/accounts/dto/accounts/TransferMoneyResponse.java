package ru.yandex.practicum.bank.clients.accounts.dto.accounts;

import lombok.Builder;

@Builder
public class TransferMoneyResponse {
    private boolean completed;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
