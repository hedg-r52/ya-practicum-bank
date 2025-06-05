package ru.yandex.practicum.bank.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class BankAccountsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankAccountsApplication.class, args);
    }
}
