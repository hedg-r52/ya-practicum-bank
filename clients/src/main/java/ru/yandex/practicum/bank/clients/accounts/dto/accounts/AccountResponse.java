package ru.yandex.practicum.bank.clients.accounts.dto.accounts;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class AccountResponse {
    private Long id;
    private Long userId;
    private String currency;
    private Double amount;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate createdAt;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate modifiedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDate modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
