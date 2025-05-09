package ru.yandex.practicum.bank.auth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OidcUser user) {
        return Map.of(
                "username", user.getPreferredUsername(),
                "email", user.getEmail(),
                "roles", user.getAuthorities()
        );
    }

}
