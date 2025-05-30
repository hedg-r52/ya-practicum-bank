package ru.yandex.practicum.bank.clients.accounts.dto.user;

import jakarta.validation.constraints.Size;

public class PasswordChangeRequest {
    @Size(min = 8)
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
