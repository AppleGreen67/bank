package ru.yandex.practicum.notification.monitoring;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MonitoringClient {
    private final MeterRegistry meterRegistry;

    public MonitoringClient(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void send(String name, String login) {
        meterRegistry.counter(name,
                "login", login
        ).increment();
    }
}
