package ru.yandex.practicum.accounts.client;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.accounts.kafka.NotificationProducer;
import ru.yandex.practicum.notification.api.NotifyMessage;

@Component
public class NotificationClient {

    private final NotificationProducer notificationProducer;

    public NotificationClient(NotificationProducer notificationProducer) {
        this.notificationProducer = notificationProducer;
    }

    public void sendMessage(NotifyMessage notifyMessage) {
        notificationProducer.sendMessage(notifyMessage);
    }
}
