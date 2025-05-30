package ru.yandex.practicum.bank.front.dto.user;

import ru.yandex.practicum.bank.front.dto.account.AccountResponseDto;

import java.util.List;

public class UserAccountsResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private List<AccountResponseDto> accounts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<AccountResponseDto> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountResponseDto> accounts) {
        this.accounts = accounts;
    }
}
