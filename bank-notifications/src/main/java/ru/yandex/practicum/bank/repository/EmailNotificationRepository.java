package ru.yandex.practicum.bank.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.yandex.practicum.bank.model.EmailNotification;

@Repository
public interface EmailNotificationRepository extends ReactiveCrudRepository<EmailNotification, Long> {
    Flux<EmailNotification> findBySent(Boolean sent);
}
