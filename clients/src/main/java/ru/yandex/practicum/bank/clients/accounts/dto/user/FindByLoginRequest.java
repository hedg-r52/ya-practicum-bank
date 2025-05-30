package ru.yandex.practicum.bank.clients.accounts.dto.user;

import lombok.Builder;

@Builder
public class FindByLoginRequest {
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
