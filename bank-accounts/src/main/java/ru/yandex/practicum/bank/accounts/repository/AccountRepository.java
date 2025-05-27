package ru.yandex.practicum.bank.accounts.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import ru.yandex.practicum.bank.accounts.model.Account;

public interface AccountRepository extends R2dbcRepository<Account, Long> {
    Flux<Account> findByUserId(Long userId);
}
