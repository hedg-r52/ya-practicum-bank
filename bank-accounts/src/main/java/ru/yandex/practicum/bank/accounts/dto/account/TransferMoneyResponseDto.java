package ru.yandex.practicum.bank.accounts.dto.account;

import lombok.Builder;

@Builder
public class TransferMoneyResponseDto {
    private boolean completed;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
