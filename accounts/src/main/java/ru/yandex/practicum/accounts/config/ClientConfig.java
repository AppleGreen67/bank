package ru.yandex.practicum.accounts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import ru.yandex.practicum.notification.client.NotificationProducer;
import ru.yandex.practicum.notification.model.NotifyMessage;

@Configuration
public class ClientConfig {
    @Bean
    public NotificationProducer notificationProducer(KafkaTemplate<String, NotifyMessage> kafkaTemplate) {
        return new NotificationProducer(kafkaTemplate);
    }
}
