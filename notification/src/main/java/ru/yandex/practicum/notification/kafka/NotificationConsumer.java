package ru.yandex.practicum.notification.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.notification.model.NotifyMessage;
import ru.yandex.practicum.notification.service.NotificationService;

@Component
public class NotificationConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationConsumer.class);

    private final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "${topic.notification.name}")
    public void print(ConsumerRecord<?, ?> record, Acknowledgment ack) {
        LOGGER.debug("Получено сообщение: ключ [{}], значение [{}]", record.key(), record.value());
        notificationService.sendMessage((NotifyMessage) record.value());
        ack.acknowledge();
    }
}
