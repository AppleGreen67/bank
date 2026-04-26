package ru.yandex.practicum.transfer.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.notification.model.NotifyMessage;
import ru.yandex.practicum.transfer.client.NotificationClient;

import java.util.UUID;

@Component
public class NotificationService {
    private final NotificationClient client;

    public NotificationService(NotificationClient client) {
        this.client = client;
    }

    public void sendMessage(String login, String message, boolean error) {
        client.sendMessage(createMessage(login, message, error));
    }

    private NotifyMessage createMessage(String login, String message, boolean error) {
        NotifyMessage notifyMessage = new NotifyMessage();
        notifyMessage.setLogin(login);
        notifyMessage.setId(UUID.randomUUID().toString());
        notifyMessage.setError(error);
        notifyMessage.setMessage("TRANSFER. " + message);
        return notifyMessage;
    }
}
