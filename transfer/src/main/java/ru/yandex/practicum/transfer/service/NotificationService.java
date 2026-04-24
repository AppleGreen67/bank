package ru.yandex.practicum.transfer.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.notification.model.NotifyMessage;
import ru.yandex.practicum.transfer.client.NotificationClient;

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
        notifyMessage.setError(error);
        notifyMessage.setMessage("TRANSFER. " + message);
        return notifyMessage;
    }
}
