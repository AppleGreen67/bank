package ru.yandex.practicum.mybankfront.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.server.domain.UserAccount;
import ru.yandex.server.domain.UserAccountSmall;

import java.util.List;

@Component
public class AccountsClient {
    private final WebClient gatewayWebClient;
    private final String gatewayBaseUrl;

    public AccountsClient(WebClient gatewayWebClient,
                          @Value("${bank.gateway.base-url}") String gatewayBaseUrl) {
        this.gatewayWebClient = gatewayWebClient;
        this.gatewayBaseUrl = gatewayBaseUrl;
    }

    public UserAccount getAccount() {
        return gatewayWebClient
                .get()
                .uri(gatewayBaseUrl + "/account")
                .retrieve()
                .bodyToMono(UserAccount.class)
                .block();
    }

    public UserAccount updateAccount(UserAccount updatedAccount) {
        return gatewayWebClient
                .post()
                .uri(gatewayBaseUrl + "/account")
                .bodyValue(updatedAccount)
                .retrieve()
                .bodyToMono(UserAccount.class)
                .block();
    }

    public List<UserAccountSmall> getAccounts() {
        return gatewayWebClient
                .get()
                .uri(gatewayBaseUrl + "/accounts")
                .retrieve()
                .bodyToFlux(UserAccountSmall.class)
                .collectList().block();
    }
}
