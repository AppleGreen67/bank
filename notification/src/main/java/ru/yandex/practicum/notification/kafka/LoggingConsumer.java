package ru.yandex.practicum.notification.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class LoggingConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingConsumer.class);

    @KafkaListener(topics = "log-events")
    public void print(ConsumerRecord<?, ?> record) {
        LOGGER.info("Получено сообщение: ключ [{}], значение [{}]", record.key(), record.value());
    }
}
