package ru.yandex.practicum.mybankfront.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.server.domain.CashRequest;

@Component
public class CashClient {
    private final WebClient gatewayWebClient;
    private final String gatewayBaseUrl;

    public CashClient(WebClient gatewayWebClient,
                      @Value("${bank.gateway.base-url}") String gatewayBaseUrl) {
        this.gatewayWebClient = gatewayWebClient;
        this.gatewayBaseUrl = gatewayBaseUrl;
    }

    public Integer updateCash(CashRequest cashRequest) {
        return gatewayWebClient
                .post()
                .uri(gatewayBaseUrl + "/cash")
                .bodyValue(cashRequest)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
    }
}
