package ru.yandex.practicum.bank.accounts.dto.user;

import jakarta.validation.constraints.Size;

public class PasswordChangeRequestDto {
    @Size(min = 8)
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
