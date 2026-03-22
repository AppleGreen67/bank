package ru.yandex.practicum.transfer.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.server.domain.NotifyMessage;

@Component
public class NotificationClient {
    private final WebClient notificationWebClient;

    @Value("${bank.notification.base-url}")
    private String baseUrl;

    public NotificationClient(WebClient notificationWebClient) {
        this.notificationWebClient = notificationWebClient;
    }

    public void sendMessage(NotifyMessage notifyMessage) {
        notificationWebClient
                .post()
                .uri(baseUrl + "/notify")
                .bodyValue(notifyMessage)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
