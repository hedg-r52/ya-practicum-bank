package ru.yandex.practicum.bank.clients.accounts.dto.accounts;

import lombok.Builder;

@Builder
public class WithdrawMoneyFromAccount {
    private Double amount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
