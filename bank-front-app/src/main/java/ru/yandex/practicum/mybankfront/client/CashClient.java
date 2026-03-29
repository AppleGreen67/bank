package ru.yandex.practicum.mybankfront.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mybankfront.exception.BankException;
import ru.yandex.server.domain.CashRequest;

@Component
public class CashClient {
    private final WebClient gatewayWebClient;
    private final String gatewayBaseUrl;

    public CashClient(WebClient gatewayWebClient,
                      @Value("${bank.cash.base-url}") String gatewayBaseUrl) {
        this.gatewayWebClient = gatewayWebClient;
        this.gatewayBaseUrl = gatewayBaseUrl;
    }

    public Integer updateCash(CashRequest cashRequest) {
        return gatewayWebClient
                .post()
                .uri(gatewayBaseUrl + "/cash")
                .bodyValue(cashRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new BankException("Ошибка обновления счета")))
                .bodyToMono(Integer.class)
                .onErrorMap(BankException.class, ex -> ex)
                .block();
    }
}
