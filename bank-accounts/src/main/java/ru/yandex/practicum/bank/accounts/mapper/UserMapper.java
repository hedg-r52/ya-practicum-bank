package ru.yandex.practicum.bank.accounts.mapper;

import lombok.AllArgsConstructor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.yandex.practicum.bank.accounts.dto.user.UserAccountsResponseDto;
import ru.yandex.practicum.bank.accounts.dto.user.UserRegisterRequestDto;
import ru.yandex.practicum.bank.accounts.dto.user.UserResponseDto;
import ru.yandex.practicum.bank.accounts.dto.user.UserUpdateRequestDto;
import ru.yandex.practicum.bank.accounts.model.User;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    public User map(UserRegisterRequestDto data) {
        return User.builder()
                .login(data.getLogin())
                .password(passwordEncoder.encode(data.getPassword()))
                .firstName(data.getFirstName())
                .lastName(data.getLastName())
                .email(data.getEmail())
                .birthDate(data.getBirthDate())
                .build();
    }

    public abstract UserResponseDto map(User user);

    public abstract UserAccountsResponseDto mapUserAccounts(User user);

    public abstract User update(UserUpdateRequestDto data, @MappingTarget User user);
}
