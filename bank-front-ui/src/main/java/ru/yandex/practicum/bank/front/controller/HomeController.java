package ru.yandex.practicum.bank.front.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "signup"; // стартовая страница — форма регистрации
    }

    @GetMapping("/main")
    public String main(Model model, OAuth2AuthenticationToken token) {
        model.addAttribute("username", token.getPrincipal().getAttribute("preferred_username"));
        return "main";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password,
                         RedirectAttributes redirectAttributes) {
        // TODO: REST-запрос в Keycloak для создания пользователя через admin REST API

        redirectAttributes.addFlashAttribute("message", "Регистрация прошла успешно!");
        return "redirect:/";
    }
}
