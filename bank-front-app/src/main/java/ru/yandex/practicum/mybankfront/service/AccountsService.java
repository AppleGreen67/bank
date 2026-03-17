package ru.yandex.practicum.mybankfront.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.server.domain.UserAccount;
import ru.yandex.server.domain.UserAccountSmall;

import java.time.LocalDate;
import java.util.List;

@Component
public class AccountsService {
    private final WebClient gatewayWebClient;
    private final String gatewayBaseUrl;

    public AccountsService(WebClient gatewayWebClient,
                           @Value("${bank.gateway.base-url}") String gatewayBaseUrl) {
        this.gatewayWebClient = gatewayWebClient;
        this.gatewayBaseUrl = gatewayBaseUrl;
    }

    public UserAccount getAccount() {
        return gatewayWebClient
                .get()
                .uri(gatewayBaseUrl + "/account")
                .retrieve()
                .bodyToMono(UserAccount.class)
                .block();
    }

    public List<UserAccountSmall> getAccounts() {
        return gatewayWebClient
                .get()
                .uri(gatewayBaseUrl + "/accounts")
                .retrieve()
                .bodyToFlux(UserAccountSmall.class)
                .collectList().block();
    }

    public UserAccount updateAccount(String name, LocalDate birthdate) {
        UserAccount userAccount = new UserAccount();
        userAccount.setName(name);
        userAccount.setBirthdate(birthdate.toString());

        return gatewayWebClient
                .post()
                .uri(gatewayBaseUrl + "/account")
                .bodyValue(userAccount)
                .retrieve()
                .bodyToMono(UserAccount.class)
                .block();
    }

//    public String submitTransfer(String fromAccountId, String toAccountId, BigDecimal amount) {
//        TransferRequest request = new TransferRequest();
//        request.setFromAccountId(fromAccountId);
//        request.setToAccountId(toAccountId);
//        request.setAmount(amount);
//
//        return gatewayWebClient
//                .post()
    //                .uri(gatewayBaseUrl + "/transfers")
//                .bodyValue(request)
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//    }
}
