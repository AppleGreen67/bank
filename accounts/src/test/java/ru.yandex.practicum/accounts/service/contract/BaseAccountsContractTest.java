package ru.yandex.practicum.accounts.service.contract;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.accounts.service.AccountsService;
import ru.yandex.practicum.accounts.service.NotificationService;
import ru.yandex.practicum.accounts.service.SumService;
import ru.yandex.practicum.accounts.service.TransferService;
import ru.yandex.practicum.accounts.service.UserService;
import ru.yandex.server.domain.UserAccount;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("contract-test")
public abstract class BaseAccountsContractTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockitoBean
    private AccountsService accountService;
    @MockitoBean
    private SumService sumService;
    @MockitoBean
    private TransferService transferService;
    @MockitoBean
    private NotificationService notificationService;
    @MockitoBean
    private UserService userService;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        String testLogin = "testLogin";

        when(userService.getCurrentLogin(any())).thenReturn(testLogin);

        UserAccount userAccount = new UserAccount();
        userAccount.setLogin(testLogin);
        userAccount.setName("testName");
        userAccount.setBirthdate("2222-22-22");
        userAccount.setSum(1);
        when(accountService.getAccount(testLogin)).thenReturn(userAccount);

        when(sumService.updateSum(eq(testLogin), any(), any())).thenReturn(15);

        when(transferService.transfer(eq(testLogin), any(), any())).thenReturn(1);

    }
}