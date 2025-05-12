package ru.yandex.practicum.bank.blocker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockResponse {
    private boolean blocked;
    private String reason;
}
