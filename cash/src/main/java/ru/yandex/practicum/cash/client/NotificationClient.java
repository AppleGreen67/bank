package ru.yandex.practicum.cash.client;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.cash.kafka.NotificationProducer;
import ru.yandex.practicum.notification.model.NotifyMessage;

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
