package ru.yandex.practicum.accounts.kafka;

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
        LOGGER.debug("Отправка сообщения в kafka...");
        kafkaTemplate.send(notification_topic, notifyMessage.getId(), notifyMessage)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        LOGGER.debug("Сообщение отправлено: офсет {}, партиция {}",
                                result.getRecordMetadata().offset(), result.getRecordMetadata().partition());
                    } else {
                        LOGGER.error("Не получилось отправить сообщение в топик {}, ошибка: {}", notification_topic, ex.getMessage());
                    }
                });
    }
}
