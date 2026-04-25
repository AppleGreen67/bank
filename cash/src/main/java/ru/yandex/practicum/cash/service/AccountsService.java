package ru.yandex.practicum.cash.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.cash.client.AccountsClient;
import ru.yandex.practicum.cash.monitoring.MonitoringClient;
import ru.yandex.server.domain.CashRequest;

import static ru.yandex.practicum.cash.monitoring.Metric.CASH_FAIL;
import static ru.yandex.practicum.cash.monitoring.Metric.CASH_SUCCESS;
import static ru.yandex.practicum.cash.monitoring.Metric.CASH_TOTAL;

@Component
public class AccountsService {
    private final AccountsClient client;
    private final MonitoringClient monitoringClient;

    public AccountsService(AccountsClient client, MonitoringClient monitoringClient) {
        this.client = client;
        this.monitoringClient = monitoringClient;
    }

    public Integer updateAccount(CashRequest request, String login) {
        monitoringClient.send(CASH_TOTAL, login);

        Integer result;
        try {
            result = client.updateSum(request, login);
            monitoringClient.send(CASH_SUCCESS, login);
        } catch (Exception e) {
            monitoringClient.send(CASH_FAIL, login);
            throw e;
        }
        return result;
    }

}
