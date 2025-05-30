package ru.yandex.practicum.transfer.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.yandex.practicum.transfer.model.TransactionStatus;
import ru.yandex.practicum.transfer.repository.dto.TransactionRepositoryDto;

import java.util.List;

public interface TransactionRepository extends ReactiveCrudRepository<TransactionRepositoryDto, Long> {
    Flux<TransactionRepositoryDto> findByStatus(TransactionStatus status);

    Flux<TransactionRepositoryDto> findByStatusInAndNotificationSent(List<TransactionStatus> statuses, boolean notificationSent);
}
