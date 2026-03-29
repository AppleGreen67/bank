package ru.yandex.practicum.mybankfront.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mybankfront.exception.BankException;
import ru.yandex.server.domain.UserAccount;
import ru.yandex.server.domain.UserAccountSmall;

import java.util.List;

@Component
public class AccountsClient {
    private final WebClient gatewayWebClient;
    private final String gatewayBaseUrl;

    public AccountsClient(WebClient gatewayWebClient,
                          @Value("${bank.accounts.base-url}") String gatewayBaseUrl) {
        this.gatewayWebClient = gatewayWebClient;
        this.gatewayBaseUrl = gatewayBaseUrl;
    }

    public UserAccount getAccount() {
        return gatewayWebClient
                .get()
                .uri(gatewayBaseUrl + "/account")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new BankException("Ошибка получения аккаунта")))
                .bodyToMono(UserAccount.class)
                .onErrorMap(BankException.class, ex -> ex)
                .block();
    }

    public UserAccount updateAccount(UserAccount updatedAccount) {
        return gatewayWebClient
                .post()
                .uri(gatewayBaseUrl + "/account")
                .bodyValue(updatedAccount)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new BankException("Ошибка обновления аккаунта")))
                .bodyToMono(UserAccount.class)
                .onErrorMap(BankException.class, ex -> ex)
                .block();
    }

    public List<UserAccountSmall> getAccounts() {
        return gatewayWebClient
                .get()
                .uri(gatewayBaseUrl + "/accounts")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new BankException("Ошибка получения аккаунтов")))
                .bodyToFlux(UserAccountSmall.class)
                .onErrorMap(BankException.class, ex -> ex)
                .collectList().block();
    }
}
