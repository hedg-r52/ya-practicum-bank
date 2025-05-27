package ru.yandex.practicum.bank.front.dto.account;

import jakarta.validation.constraints.NotNull;

public class AccountCreateRequestDto {
    @NotNull
    private Long userId;
    @NotNull
    private String currency;

    public AccountCreateRequestDto(Long userId, String currency) {
        this.userId = userId;
        this.currency = currency;
    }
}
