package ru.yandex.practicum.accounts.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.server.domain.CashRequest;
import ru.yandex.server.domain.NotifyMessage;

@Component
public class NotificationClient {
    private final WebClient notificationWebClient;

    public NotificationClient(WebClient accountsWebClient) {
        this.notificationWebClient = accountsWebClient;
    }

    public void sendMessage(String login) {
        NotifyMessage notifyMessage = new NotifyMessage();
        notifyMessage.setLogin(login);
        notifyMessage.setMessage("I'm message");
        notifyMessage.setError(false);

        notificationWebClient
                .post()
                .uri("/notify")
                .bodyValue(notifyMessage)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
