package ru.yandex.practicum.cash.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.server.domain.CashRequest;
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

    public Integer updateSum(CashRequest request) {
        return gatewayWebClient
                .post()
                .uri(gatewayBaseUrl + "/change")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
    }
}
