package ru.yandex.practicum.accounts.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LogMessageProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogMessageProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public LogMessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

//    @Scheduled(fixedDelay = 1000)
    public void sendMessage() {
        try {
            LOGGER.info("Sending message to kafka");
            kafkaTemplate.send("log-events", "Ключ: " + LocalDateTime.now(), "Всем привет!");
        } catch (Exception e) {
            LOGGER.error("Error sending log events", e);
        }
    }
}
