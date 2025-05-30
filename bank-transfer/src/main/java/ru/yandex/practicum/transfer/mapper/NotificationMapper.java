package ru.yandex.practicum.transfer.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.bank.clients.notification.dto.EmailNotificationRequest;
import ru.yandex.practicum.transfer.exception.UnknownTransactionTypeException;
import ru.yandex.practicum.transfer.model.SelfTransferTransaction;
import ru.yandex.practicum.transfer.model.Transaction;
import ru.yandex.practicum.transfer.model.TransferAnotherUserTransaction;

@Component
public class NotificationMapper {
    public EmailNotificationRequest map(Transaction transaction, String email, String recipientName) {
        String subject = switch (transaction) {
            case SelfTransferTransaction selfTransferTransaction ->
                    String.format("Результат выполнения перевода со счета %d на счет %d",
                            selfTransferTransaction.getAccountId(),
                            selfTransferTransaction.getReceiverAccountId());
            case TransferAnotherUserTransaction transferToOtherUserTransaction ->
                    String.format("Результат выполнения перевода со счета %d клиенту %s на счет %d",
                            transferToOtherUserTransaction.getAccountId(),
                            recipientName,
                            transferToOtherUserTransaction.getReceiverAccountId());
            default -> throw new UnknownTransactionTypeException("N/A");
        };

        EmailNotificationRequest request = EmailNotificationRequest
                .builder()
                .recipient(email)
                .subject(subject)
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
