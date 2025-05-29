package ru.yandex.practicum.bank.cash.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.bank.cash.exception.UnknownTransactionTypeException;
import ru.yandex.practicum.bank.cash.model.DepositTransaction;
import ru.yandex.practicum.bank.cash.model.Transaction;
import ru.yandex.practicum.bank.cash.model.WithdrawTransaction;
import ru.yandex.practicum.bank.clients.notification.dto.EmailNotificationRequest;

@Component
public class NotificationMapper {
    public EmailNotificationRequest map(Transaction transaction, String email) {
        String operationType = switch (transaction) {
            case DepositTransaction depositTransaction -> "внесения";
            case WithdrawTransaction withdrawalTransaction -> "снятия";
            default -> throw new UnknownTransactionTypeException();
        };

        EmailNotificationRequest request = EmailNotificationRequest
                .builder()
                .recipient(email)
                .subject(String.format("Результат выполнения операции %s денежных средств по вашему счету %d",
                        operationType, transaction.getAccountId()))
                .build();

        switch (transaction.getStatus()) {
            case BLOCKED -> request.setMessage("Операция заблокирована");
            case FAILED -> request.setMessage("Ошибка совершения операции! Пожалуйста попробуйте позже...");
            case COMPLETED -> request.setMessage("Операция выполнена успешно");
            case NOT_ENOUGH_MONEY -> request.setMessage("Недостаточно денег для совершения операции");
        }
        return request;
    }
}
