package ru.yandex.practicum.cash.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.server.domain.CashRequest;

@Component
public class AccountsClient {
    private final WebClient accountsWebClient;

    public AccountsClient(WebClient accountsWebClient) {
        this.accountsWebClient = accountsWebClient;
    }

    public Integer updateSum(CashRequest request, String login) {
        return accountsWebClient
                .post()
                .uri("/{login}/change", login)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
    }
}
