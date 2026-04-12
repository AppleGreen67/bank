//package ru.yandex.practicum.notification.service.contract;
//
//import io.restassured.module.mockmvc.RestAssuredMockMvc;
//import org.junit.jupiter.api.BeforeEach;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import ru.yandex.practicum.notification.service.NotificationService;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doNothing;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("contract-test")
//public class BaseNotificationContractTest {
//    @Autowired
//    protected MockMvc mockMvc;
//
//    @MockitoBean
//    private NotificationService notificationService;
//
//    @BeforeEach
//    void setup() {
//        RestAssuredMockMvc.mockMvc(mockMvc);
//
//        doNothing().when(notificationService).sendMessage(any());
//    }
//}
