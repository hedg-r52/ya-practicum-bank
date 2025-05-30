package ru.yandex.practicum.bank.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.yandex.practicum.bank.dto.EmailNotificationRequestDto;
import ru.yandex.practicum.bank.dto.EmailNotificationResponseDto;
import ru.yandex.practicum.bank.model.EmailNotification;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class NotificationMapper {

    public abstract EmailNotificationResponseDto map(EmailNotification notification);

    public abstract EmailNotification map(EmailNotificationRequestDto request);

    @AfterMapping
    protected void setSentToFalse(@MappingTarget EmailNotification notification) {
        if (notification.getSent() == null) {
            notification.setSent(false);
        }
    }

}
