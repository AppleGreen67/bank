package ru.yandex.practicum.transfer.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.notification.model.NotifyMessage;

@Component
public class NotificationProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationProducer.class);

    @Value("${topic.notification.name}")
    private String notification_topic;

    private final KafkaTemplate<String, NotifyMessage> kafkaTemplate;

    public NotificationProducer(KafkaTemplate<String, NotifyMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(NotifyMessage notifyMessage) {
        try {
            LOGGER.debug("Отправка сообщения в kafka...");
            kafkaTemplate.send(notification_topic, notifyMessage.getId(), notifyMessage);
        } catch (Exception e) {
            LOGGER.error("Error sending log events", e);
        }
    }
}
