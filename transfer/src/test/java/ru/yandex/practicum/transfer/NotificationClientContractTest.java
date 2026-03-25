package ru.yandex.practicum.transfer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.ActiveProfiles;
import ru.yandex.practicum.transfer.client.NotificationClient;
import ru.yandex.server.domain.NotifyMessage;
import ru.yandex.server.domain.TransferRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("contract-test")
@AutoConfigureStubRunner(
        ids = "ru.yandex.practicum:notification:+:stubs:8087",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
class NotificationClientContractTest {

    @Autowired
    private NotificationClient notificationClient;

    @Test
    void updateSumTest() {
        notificationClient.sendMessage(new NotifyMessage("some_message", true));
    }
}
