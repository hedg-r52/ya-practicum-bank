package ru.yandex.practicum.bank.front.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/exchange")
public class ExchangeController {

    @GetMapping
    public Mono<String> getRates(Model model) {
        model.addAttribute("rates", List.of());
        return Mono.just("exchange");
    }

}
