package ru.yandex.practicum.cash.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.cash.client.AccountsClient;
import ru.yandex.server.domain.CashRequest;

@Component
public class AccountsService {
    private final AccountsClient client;

    public AccountsService(AccountsClient client) {
        this.client = client;
    }

    public Integer updateAccount(CashRequest request) {
        return client.updateSum(request);
    }

}
