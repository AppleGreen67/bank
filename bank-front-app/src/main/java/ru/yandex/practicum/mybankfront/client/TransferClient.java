package ru.yandex.practicum.mybankfront.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.server.domain.TransferRequest;

@Component
public class TransferClient {
    private final WebClient gatewayWebClient;
    private final String gatewayBaseUrl;

    public TransferClient(WebClient gatewayWebClient,
                          @Value("${bank.gateway.base-url}") String gatewayBaseUrl) {
        this.gatewayWebClient = gatewayWebClient;
        this.gatewayBaseUrl = gatewayBaseUrl;
    }

    public Integer transfer(TransferRequest request) {
        return gatewayWebClient
                .post()
                .uri(gatewayBaseUrl + "/transfer")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
    }
}
