package ru.yandex.practicum.accounts.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.accounts.client.NotificationClient;

@Component
public class NotificationService {
    private final NotificationClient client;

    public NotificationService(NotificationClient client) {
        this.client = client;
    }

    public void sendMessage(String login) {
        client.sendMessage(login);
    }
}
