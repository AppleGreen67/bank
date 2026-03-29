package ru.yandex.practicum.mybankfront.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mybankfront.exception.BankException;
import ru.yandex.server.domain.TransferRequest;

@Component
public class TransferClient {
    private final WebClient gatewayWebClient;
    private final String gatewayBaseUrl;

    public TransferClient(WebClient gatewayWebClient,
                          @Value("${bank.transfer.base-url}") String gatewayBaseUrl) {
        this.gatewayWebClient = gatewayWebClient;
        this.gatewayBaseUrl = gatewayBaseUrl;
    }

    public Integer transfer(TransferRequest request) {
        return gatewayWebClient
                .post()
                .uri(gatewayBaseUrl + "/transfer")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new BankException("Ошибка перевода")))
                .bodyToMono(Integer.class)
                .block();
    }
}
