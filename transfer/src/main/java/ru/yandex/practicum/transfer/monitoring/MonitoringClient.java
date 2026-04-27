package ru.yandex.practicum.transfer.monitoring;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MonitoringClient {
    private final MeterRegistry meterRegistry;

    public MonitoringClient(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void send(String name, String senderLogin, String recipientLogin) {
        meterRegistry.counter(name,
                "senderLogin", senderLogin,
                "recipientLogin", recipientLogin
        ).increment();
    }
}
