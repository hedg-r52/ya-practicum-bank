package ru.yandex.practicum.bank.clients.accounts.dto.accounts;

import jakarta.validation.constraints.NotNull;

public class AccountCreateRequest {
    @NotNull
    private Long userId;
    @NotNull
    private String currency;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
