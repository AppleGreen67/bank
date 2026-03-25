package ru.yandex.practicum.cash;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.ActiveProfiles;
import ru.yandex.practicum.cash.client.AccountsClient;
import ru.yandex.server.domain.CashRequest;

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
        CashRequest request = new CashRequest();
        request.setSum(5);
        request.setAction("PUT");

        Integer result = accountsClient.updateSum(request, "testLogin");

        assertEquals(15, result);
    }
}
