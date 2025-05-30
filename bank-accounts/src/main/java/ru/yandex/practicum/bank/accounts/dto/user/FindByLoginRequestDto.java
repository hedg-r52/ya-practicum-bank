package ru.yandex.practicum.bank.accounts.dto.user;

public class FindByLoginRequestDto {
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
