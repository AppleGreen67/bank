package ru.yandex.practicum.transfer.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.transfer.client.AccountsClient;
import ru.yandex.practicum.transfer.monitoring.MonitoringClient;
import ru.yandex.server.domain.TransferRequest;

import static ru.yandex.practicum.transfer.monitoring.Metric.TRANSFER_FAIL;
import static ru.yandex.practicum.transfer.monitoring.Metric.TRANSFER_SUCCESS;
import static ru.yandex.practicum.transfer.monitoring.Metric.TRANSFER_TOTAL;

@Component
public class TransferToAccountsService {
    private final AccountsClient client;
    private final MonitoringClient monitoringClient;

    public TransferToAccountsService(AccountsClient client, MonitoringClient monitoringClient) {
        this.client = client;
        this.monitoringClient = monitoringClient;
    }

    public Integer transfer(TransferRequest request, String login) {
        monitoringClient.send(TRANSFER_TOTAL, login, request.getLogin());

        Integer result;
        try {
            result = client.updateSum(request, login);
            monitoringClient.send(TRANSFER_SUCCESS, login, request.getLogin());
        } catch (Exception e) {
            monitoringClient.send(TRANSFER_FAIL, login, request.getLogin());
            throw e;
        }
        return result;
    }

}
