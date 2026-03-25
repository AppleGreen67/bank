package ru.yandex.practicum.mybankfront.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.mybankfront.client.TransferClient;
import ru.yandex.server.domain.TransferRequest;

@Component
public class TransferService {
    private final TransferClient client;

    public TransferService(TransferClient client) {
        this.client = client;
    }

    public boolean transfer(int value, String login) {
        TransferRequest request = new TransferRequest();
        request.setSum(value);
        request.setLogin(login);

        try {
            client.transfer(request);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
