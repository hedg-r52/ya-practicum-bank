package ru.yandex.practicum.bank.clients.accounts.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String email;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthDate;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
