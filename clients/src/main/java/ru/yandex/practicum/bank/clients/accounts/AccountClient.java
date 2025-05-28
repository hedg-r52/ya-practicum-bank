package ru.yandex.practicum.bank.clients.accounts;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bank.clients.AbstractClient;
import ru.yandex.practicum.bank.clients.accounts.dto.accounts.*;
import ru.yandex.practicum.bank.clients.accounts.dto.user.*;
import ru.yandex.practicum.bank.clients.exception.MoneyException;

@RequiredArgsConstructor
public class AccountClient extends AbstractClient {
    private final String baseUrl;
    private final WebClient webClient;

    public Mono<UserResponse> findUserByLogin(FindByLoginRequest request) {
        return Mono.just(webClient.post()
                        .uri(baseUrl + "/user/find/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .retrieve())
            .flatMap(response -> responseToMono(response, UserResponse.class));
    }

    public Mono<UserResponse> findUserByAccountId(Long accountId) {
        return Mono.just(webClient.get()
                        .uri(baseUrl + "/user/find/account/{accountId}", accountId)
                        .retrieve())
                .flatMap(response -> responseToMono(response, UserResponse.class));
    }

    public Mono<UserResponse> registerUser(UserRegisterRequest request) {
        return Mono.just(webClient.post()
                        .uri(baseUrl + "/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .retrieve())
                .flatMap(response -> responseToMono(response, UserResponse.class));
    }

    public Mono<UserResponse> updateUser(Long userId, UserUpdateRequest request) {
        return Mono.just(webClient.put()
                        .uri(baseUrl + "/user/{userId}", userId)
                        .bodyValue(request)
                        .retrieve())
                .flatMap(response -> responseToMono(response, UserResponse.class));
    }

    public Mono<UserResponse> changePassword(Long userId, PasswordChangeRequest request) {
        return Mono.just(webClient.put()
                        .uri(baseUrl + "/user/{userId}/password", userId)
                        .bodyValue(request)
                        .retrieve())
                .flatMap(response -> responseToMono(response, UserResponse.class));
    }

    public Flux<UserAccountsResponse> getUserAccounts() {
        return Flux.just(webClient.get()
                        .uri(baseUrl + "/user/accounts")
                        .retrieve())
                .flatMap(response -> responseToFlux(response, UserAccountsResponse.class));
    }

    public Mono<AccountResponse> createAccount(AccountCreateRequest request) {
        return Mono.just(webClient
                        .post()
                        .uri(baseUrl + "/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .retrieve()
                )
                .flatMap(response -> responseToMono(response, AccountResponse.class));
    }

    public Mono<AccountResponse> getAccountById(Long accountId) {
        return Mono.just(webClient
                        .get()
                        .uri(baseUrl + "/accounts/" + accountId)
                        .retrieve()
                )
                .flatMap(response -> responseToMono(response, AccountResponse.class));
    }

    public Flux<AccountResponse> getUserAccounts(Long userId) {
        return Flux.just(webClient
                        .get()
                        .uri(baseUrl + "/accounts/user/" + userId)
                        .retrieve()
                )
                .flatMap(response -> responseToFlux(response, AccountResponse.class));
    }

    public Mono<AccountResponse> depositMoney(Long accountId, DepositMoneyToAccount request) {
        return Mono.just(webClient
                        .put()
                        .uri(baseUrl + "/accounts/" + accountId + "/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .retrieve()
                )
                .flatMap(response -> responseToMono(response, AccountResponse.class));
    }

    public Mono<AccountResponse> withdrawMoney(Long accountId, WithdrawMoneyFromAccount request) {
        return Mono.just(webClient
                        .put()
                        .uri(baseUrl + "/accounts/" + accountId + "/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .retrieve()
                        .onStatus(
                                HttpStatus.PAYMENT_REQUIRED::equals,
                                response -> response.bodyToMono(String.class).map(MoneyException::new)
                        )
                )
                .flatMap(response -> responseToMono(response, AccountResponse.class));
    }

    public Mono<TransferMoneyResponse> transferMoney(TransferMoneyRequest request) {
        return Mono.just(webClient
                        .put()
                        .uri(baseUrl + "/accounts/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .retrieve()
                        .onStatus(
                                HttpStatus.PAYMENT_REQUIRED::equals,
                                response -> response.bodyToMono(String.class).map(MoneyException::new)
                        )
                )
                .flatMap(responseSpec -> responseToMono(responseSpec, TransferMoneyResponse.class));
    }

    public Mono<Void> deleteAccount(Long accountId) {
        return Mono.just(webClient
                        .delete()
                        .uri(baseUrl + "/accounts/" + accountId)
                        .retrieve()
                )
                .flatMap(response -> responseToMono(response, Void.class));
    }

}
