package ru.yandex.practicum.bank.clients.accounts.dto.accounts;

import jakarta.validation.constraints.NotNull;

public class AccountCreateRequest {
    @NotNull
    private Long id;
    @NotNull
    private String currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
