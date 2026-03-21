package ru.yandex.practicum.transfer.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.transfer.client.AccountsClient;
import ru.yandex.server.domain.TransferRequest;

@Component
public class TransferToAccountsService {
    private final AccountsClient client;

    public TransferToAccountsService(AccountsClient client) {
        this.client = client;
    }

    public Integer transfer(TransferRequest request, String login) {
        return client.updateSum(request, login);
    }

}
