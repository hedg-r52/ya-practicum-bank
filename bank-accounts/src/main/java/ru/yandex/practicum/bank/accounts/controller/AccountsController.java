package ru.yandex.practicum.bank.accounts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AccountsController {

    @GetMapping("/api/accounts")
    public List<Map<String, String>> getAccounts() {
        return List.of(
                Map.of("id", "1", "type", "checking"),
                Map.of("id", "2", "type", "savings")
        );
    }

}
