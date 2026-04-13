package ru.yandex.practicum.transfer.client;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.notification.model.NotifyMessage;
import ru.yandex.practicum.transfer.kafka.NotificationProducer;

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
