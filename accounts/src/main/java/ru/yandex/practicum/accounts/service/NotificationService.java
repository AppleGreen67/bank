package ru.yandex.practicum.accounts.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.accounts.client.NotificationClient;
import ru.yandex.practicum.notification.model.NotifyMessage;

import java.util.UUID;

@Component
public class NotificationService {
    private final NotificationClient client;

    public NotificationService(NotificationClient client) {
        this.client = client;
    }

    public void sendMessage(String message, boolean error) {
        client.sendMessage(createMessage(message, error));
    }

    private NotifyMessage createMessage(String message, boolean error) {
        NotifyMessage notifyMessage = new NotifyMessage();
        notifyMessage.setId(UUID.randomUUID().toString());
        notifyMessage.setError(error);
        notifyMessage.setMessage("ACCOUNTS. " + message);
        return notifyMessage;
    }
}
