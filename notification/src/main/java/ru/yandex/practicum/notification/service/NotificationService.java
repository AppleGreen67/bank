package ru.yandex.practicum.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.notification.model.NotifyMessage;
import ru.yandex.practicum.notification.monitoring.MonitoringClient;

import java.util.concurrent.ThreadLocalRandom;

import static ru.yandex.practicum.notification.monitoring.Metric.NOTIFICATION_FAIL;
import static ru.yandex.practicum.notification.monitoring.Metric.NOTIFICATION_SUCCESS;
import static ru.yandex.practicum.notification.monitoring.Metric.NOTIFICATION_TOTAL;

@Component
public class NotificationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class.getName());

    private final MonitoringClient monitoringClient;

    public NotificationService(MonitoringClient monitoringClient) {
        this.monitoringClient = monitoringClient;
    }

    public void sendMessage(NotifyMessage request) {
        monitoringClient.send(NOTIFICATION_TOTAL, request.getLogin());

        if (trySendMessage(request))
            monitoringClient.send(NOTIFICATION_SUCCESS, request.getLogin());
        else
            monitoringClient.send(NOTIFICATION_FAIL, request.getLogin());
    }

    private boolean trySendMessage(NotifyMessage request) {
        if (ThreadLocalRandom.current().nextInt(10) >= 7)
            return false;

        if (Boolean.FALSE.equals(request.getError()))
            LOGGER.info(request.getMessage());
        else
            LOGGER.error(request.getMessage());

        return true;
    }
}
