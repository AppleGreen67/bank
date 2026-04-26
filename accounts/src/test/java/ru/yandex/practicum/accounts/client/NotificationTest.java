package ru.yandex.practicum.accounts.client;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.hamcrest.KafkaMatchers;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.yandex.practicum.accounts.service.contract.PostgreTestContainer;
import ru.yandex.practicum.notification.client.NotificationProducer;
import ru.yandex.practicum.notification.model.NotifyMessage;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@EmbeddedKafka(topics = {"${topic.notification.name}"})
@Testcontainers
@ImportTestcontainers(PostgreTestContainer.class)
public class NotificationTest {

    @Value("${topic.notification.name}")
    public String TEST_TOPIC_NAME;

    @Autowired
    private NotificationProducer producer;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Test
    public void testSimpleProcessor() {
        Map<String, Object> props = new HashMap<>();
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "ru.yandex.practicum.notification.model");
        props.putAll(KafkaTestUtils.consumerProps("yandex-practicum-test", "true", embeddedKafkaBroker));

        try (var consumerForTest = new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>()
        ).createConsumer()) {
            consumerForTest.subscribe(List.of(TEST_TOPIC_NAME));

            String key = UUID.randomUUID().toString();
            NotifyMessage testMessage = new NotifyMessage(key,"login", "test message", null);

            producer.sendMessage(testMessage);

            ConsumerRecord<String, Object> record = KafkaTestUtils.getSingleRecord(consumerForTest, TEST_TOPIC_NAME, Duration.ofSeconds(5));

            assertThat(record, KafkaMatchers.hasKey(key));
            NotifyMessage value = (NotifyMessage)((ConsumerRecord<?, ? super NotifyMessage>) record).value();
            assertEquals(key, value.getId());
            assertEquals("test message", value.getMessage());
            assertNull(value.getError());

        }
    }
}
