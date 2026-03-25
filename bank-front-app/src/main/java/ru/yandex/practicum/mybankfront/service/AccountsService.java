package ru.yandex.practicum.mybankfront.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.mybankfront.client.AccountsClient;
import ru.yandex.server.domain.UserAccount;
import ru.yandex.server.domain.UserAccountSmall;

import java.time.LocalDate;
import java.util.List;

@Component
public class AccountsService {

    private final AccountsClient client;

    public AccountsService(AccountsClient client) {
        this.client = client;
    }

    public UserAccount getAccount() {
        return client.getAccount();
    }

    public List<UserAccountSmall> getAccounts() {
        return client.getAccounts();
    }

    public UserAccount updateAccount(String name, LocalDate birthdate) {
        UserAccount userAccount = new UserAccount();
        userAccount.setName(name);
        userAccount.setBirthdate(birthdate.toString());

        return client.updateAccount(userAccount);
    }

}
