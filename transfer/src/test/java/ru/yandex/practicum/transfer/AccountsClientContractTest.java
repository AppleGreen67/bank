package ru.yandex.practicum.transfer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.ActiveProfiles;
import ru.yandex.practicum.transfer.client.AccountsClient;
import ru.yandex.server.domain.TransferRequest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("contract-test")
@AutoConfigureStubRunner(
        ids = "ru.yandex.practicum:accounts:+:stubs:8082",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
class AccountsClientContractTest {

    @Autowired
    private AccountsClient accountsClient;

    @Test
    void updateSumTest() {
        TransferRequest request = new TransferRequest();
        request.setSum(new BigDecimal(1));
        request.setLogin("ACC-001");

        Integer result = accountsClient.updateSum(request, "testLogin");

        assertEquals(1, result);
    }
}
