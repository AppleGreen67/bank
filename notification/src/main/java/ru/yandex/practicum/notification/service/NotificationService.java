package ru.yandex.practicum.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.server.domain.NotifyMessage;

@Component
public class NotificationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class.getName());

    public void sendMessage(NotifyMessage request) {
        if (Boolean.FALSE.equals(request.getError()))
            LOGGER.info("{}", request.getMessage());
        else
            LOGGER.info("{}", request.getMessage());
    }
}
