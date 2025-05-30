package ru.yandex.practicum.bank.accounts.dto.account;

public class AccountCreateRequestDto {
    private Long userId;
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
