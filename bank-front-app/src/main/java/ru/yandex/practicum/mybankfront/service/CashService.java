package ru.yandex.practicum.mybankfront.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.mybankfront.client.CashClient;
import ru.yandex.practicum.mybankfront.controller.dto.CashAction;
import ru.yandex.server.domain.CashRequest;

import java.math.BigDecimal;

@Component
public class CashService {
    private final CashClient client;

    public CashService(CashClient client) {
        this.client = client;
    }

    public boolean updateCash(int value, CashAction action) {
        CashRequest cashRequest = new CashRequest();
        cashRequest.setSum(new BigDecimal(value));
        cashRequest.setAction(action.name());

        try {
            client.updateCash(cashRequest);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
