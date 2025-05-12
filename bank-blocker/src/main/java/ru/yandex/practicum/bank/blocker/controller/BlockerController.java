package ru.yandex.practicum.bank.blocker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.blocker.dto.BlockResponse;
import ru.yandex.practicum.bank.blocker.dto.Operation;
import ru.yandex.practicum.bank.blocker.service.BlockerService;

@RestController
@RequestMapping("/blocker")
@RequiredArgsConstructor
public class BlockerController {

    private final BlockerService blockerService;

    @PostMapping("/check")
    public Mono<ResponseEntity<BlockResponse>> checkOperation(@RequestBody Operation operation) {
        return blockerService.evaluate(operation)
                .map(ResponseEntity::ok);
    }
}