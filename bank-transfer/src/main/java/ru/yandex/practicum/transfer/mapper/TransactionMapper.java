package ru.yandex.practicum.transfer.mapper;


import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ReportingPolicy;
import ru.yandex.practicum.bank.clients.blocker.dto.SelfTransferTransactionRequest;
import ru.yandex.practicum.bank.clients.blocker.dto.TransferAnotherUserTransactionRequest;
import ru.yandex.practicum.transfer.dto.TransactionRequestDto;
import ru.yandex.practicum.transfer.dto.TransactionResponseDto;
import ru.yandex.practicum.transfer.exception.UnknownTransactionTypeException;
import ru.yandex.practicum.transfer.model.SelfTransferTransaction;
import ru.yandex.practicum.transfer.model.Transaction;
import ru.yandex.practicum.transfer.model.TransactionType;
import ru.yandex.practicum.transfer.model.TransferAnotherUserTransaction;
import ru.yandex.practicum.transfer.repository.dto.TransactionRepositoryDto;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TransactionMapper {
    public abstract TransactionResponseDto map(Transaction transaction);

    public abstract Transaction map(TransactionType transactionType, TransactionRequestDto transaction);

    public abstract Transaction map(TransactionRepositoryDto transaction);

    public abstract TransactionRepositoryDto mapToDb(Transaction transaction);

    public abstract SelfTransferTransactionRequest map(SelfTransferTransaction transaction);

    public abstract TransferAnotherUserTransactionRequest map(TransferAnotherUserTransaction transaction);

    @ObjectFactory
    public Transaction createTransactionFromRequest(TransactionType transactionType, TransactionRequestDto request) {
        return transactionTypeToClass(transactionType);
    }

    @ObjectFactory
    public Transaction createTransactionFromDatabase(TransactionRepositoryDto transaction) {
        return transactionTypeToClass(transaction.getType());
    }

    @AfterMapping
    protected void afterMappingResponse(Transaction transaction,
                                        @MappingTarget TransactionResponseDto transactionResponseDTO) {
        transactionResponseDTO.setType(transactionClassToTransactionType(transaction));
    }

    @AfterMapping
    protected void afterMappingToRepo(Transaction transaction,
                                      @MappingTarget TransactionRepositoryDto transactionRepositoryDTO) {
        transactionRepositoryDTO.setType(transactionClassToTransactionType(transaction));
    }

    protected Transaction transactionTypeToClass(TransactionType transactionType) {
        return switch (transactionType) {
            case SELF_TRANSFER -> new SelfTransferTransaction();
            case TRANSFER_TO_ANOTHER_USER -> new TransferAnotherUserTransaction();
        };
    }

    protected TransactionType transactionClassToTransactionType(Transaction transaction) {
        var name = transaction.getClass().getSimpleName()
                .replace("Transaction", "")
                .toUpperCase();

        return switch (name) {
            case "SELFTRANSFER" -> TransactionType.SELF_TRANSFER;
            case "TRANSFERTOANOTHERUSER" -> TransactionType.TRANSFER_TO_ANOTHER_USER;
            default -> throw new UnknownTransactionTypeException(name);
        };
    }
}
