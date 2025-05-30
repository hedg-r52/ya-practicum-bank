package ru.yandex.practicum.bank.blocker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.yandex.practicum.bank.blocker.dto.DepositTransactionRequestDto;
import ru.yandex.practicum.bank.blocker.dto.SelfTransferTransactionRequestDto;
import ru.yandex.practicum.bank.blocker.dto.TransferAnotherUserTransactionRequestDto;
import ru.yandex.practicum.bank.blocker.dto.WithdrawTransactionRequestDto;
import ru.yandex.practicum.bank.blocker.model.DepositTransaction;
import ru.yandex.practicum.bank.blocker.model.SelfTransferTransaction;
import ru.yandex.practicum.bank.blocker.model.TransferToOtherUserTransaction;
import ru.yandex.practicum.bank.blocker.model.WithdrawTransaction;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TransactionMapper {
    DepositTransaction map(DepositTransactionRequestDto transaction);

    WithdrawTransaction map(WithdrawTransactionRequestDto transaction);

    SelfTransferTransaction map(SelfTransferTransactionRequestDto transaction);

    TransferToOtherUserTransaction map(TransferAnotherUserTransactionRequestDto transaction);
}
