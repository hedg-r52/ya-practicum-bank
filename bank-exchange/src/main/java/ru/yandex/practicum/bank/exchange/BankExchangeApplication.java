package ru.yandex.practicum.bank.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BankExchangeApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankExchangeApplication.class, args);
    }
}
