package ru.yandex.practicum.bank.cash.mapper;


import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ReportingPolicy;
import ru.yandex.practicum.bank.cash.dto.CashTransactionRequestDto;
import ru.yandex.practicum.bank.cash.dto.CashTransactionResponseDto;
import ru.yandex.practicum.bank.cash.model.DepositTransaction;
import ru.yandex.practicum.bank.cash.model.Transaction;
import ru.yandex.practicum.bank.cash.model.TransactionType;
import ru.yandex.practicum.bank.cash.model.WithdrawTransaction;
import ru.yandex.practicum.bank.cash.repository.dto.TransactionRepositoryDto;
import ru.yandex.practicum.bank.clients.blocker.dto.DepositTransactionRequest;
import ru.yandex.practicum.bank.clients.blocker.dto.WithdrawTransactionRequest;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TransactionMapper {
    public abstract CashTransactionResponseDto map(Transaction transaction);

    public abstract Transaction map(TransactionType type, CashTransactionRequestDto request);

    public abstract Transaction map(TransactionRepositoryDto transaction);

    public abstract TransactionRepositoryDto mapToDb(Transaction transaction);

    public abstract DepositTransactionRequest map(DepositTransaction depositTransaction);

    public abstract WithdrawTransactionRequest map(WithdrawTransaction depositTransaction);

    @ObjectFactory
    public Transaction createTransactionFromRequest(TransactionType transactionType, CashTransactionRequestDto request) {
        return transactionTypeToClass(transactionType);
    }

    @ObjectFactory
    public Transaction createTransactionFromDatabase(TransactionRepositoryDto transaction) {
        return transactionTypeToClass(transaction.getType());
    }

    @AfterMapping
    protected void afterMappingResponse(Transaction transaction,
                                        @MappingTarget CashTransactionResponseDto transactionResponseDTO) {
        transactionResponseDTO.setType(transactionClassToTransactionType(transaction));
    }

    @AfterMapping
    protected void afterMappingToRepo(Transaction transaction,
                                      @MappingTarget TransactionRepositoryDto transactionRepositoryDTO) {
        transactionRepositoryDTO.setType(transactionClassToTransactionType(transaction));
    }

    protected Transaction transactionTypeToClass(TransactionType transactionType) {
        return switch (transactionType) {
            case DEPOSIT -> new DepositTransaction();
            case WITHDRAW -> new WithdrawTransaction();
        };
    }

    protected TransactionType transactionClassToTransactionType(Transaction transaction) {
        var name = transaction.getClass().getSimpleName()
                .replace("Transaction", "")
                .toUpperCase();

        return TransactionType.valueOf(name);
    }
}
