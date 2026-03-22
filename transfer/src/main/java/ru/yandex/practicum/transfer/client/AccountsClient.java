package ru.yandex.practicum.transfer.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.server.domain.TransferRequest;

@Component
public class AccountsClient {
    private final WebClient webClient;

    @Value("${bank.accounts.base-url}")
    private String accountsBaseUrl;

    public AccountsClient(WebClient accountsWebClient) {
        this.webClient = accountsWebClient;
    }

    public Integer updateSum(TransferRequest request, String login) {
        return webClient
                .post()
                .uri(accountsBaseUrl + "/account/{login}/transfer", login)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
    }
}
