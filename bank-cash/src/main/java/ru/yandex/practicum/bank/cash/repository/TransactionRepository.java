package ru.yandex.practicum.bank.cash.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.yandex.practicum.bank.cash.model.TransactionStatus;
import ru.yandex.practicum.bank.cash.repository.dto.TransactionRepositoryDto;

import java.util.Collection;
import java.util.List;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<TransactionRepositoryDto, Long> {
    Flux<TransactionRepositoryDto> findByStatus(TransactionStatus status);

    Flux<TransactionRepositoryDto> findByStatusInAndNotificationSent(List<TransactionStatus> statuses, Boolean notificationSent);
}
