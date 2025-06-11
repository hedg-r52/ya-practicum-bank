package ru.yandex.practicum.bank.clients.blocker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DecisionResponse {

    public DecisionResponse() {}

    @JsonProperty("isBlocked")
    private boolean isBlocked;

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
