package ru.yandex.practicum.transfer.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.server.domain.TransferRequest;

@Component
public class AccountsClient {
    private final WebClient accountsWebClient;

    public AccountsClient(WebClient accountsWebClient) {
        this.accountsWebClient = accountsWebClient;
    }

    public Integer updateSum(TransferRequest request, String login) {
        return accountsWebClient
                .post()
                .uri("/account/{login}/transfer", login)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
    }
}
