package ru.yandex.practicum.bank.blocker.model;

public class TransferToOtherUserTransaction extends Transaction {
    public Long getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(Long receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    private Long receiverAccountId;
}
