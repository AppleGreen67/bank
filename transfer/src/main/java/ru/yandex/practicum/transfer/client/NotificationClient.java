package ru.yandex.practicum.transfer.client;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.yandex.server.domain.CashRequest;
import ru.yandex.server.domain.NotifyMessage;

@Component
public class NotificationClient {
    private final WebClient notificationWebClient;

    @Value("${bank.notification.base-url}")
    private String baseUrl;
    @Value("${bank.notification.timeout}")
    private Long notificationTimeout;

    private final CircuitBreaker circuitBreaker;
    private final TimeLimiter timeLimiter;

    public NotificationClient(WebClient notificationWebClient, @Qualifier("notificationCircuitBreaker") CircuitBreaker circuitBreaker,
                              TimeLimiter timeLimiter) {
        this.notificationWebClient = notificationWebClient;
        this.circuitBreaker = circuitBreaker;
        this.timeLimiter = timeLimiter;
    }

    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "notificationService", fallbackMethod = "sendMessageFallback")
    @io.github.resilience4j.retry.annotation.Retry(name = "notificationService", fallbackMethod = "sendMessageFallback")
    public void sendMessage(NotifyMessage notifyMessage) {
        notificationWebClient
                .post()
                .uri(baseUrl + "/notify")
                .bodyValue(notifyMessage)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    private Mono<Integer> sendMessageFallback(CashRequest request, String login, Exception e) {
        System.err.println("Circuit breaker открыт или произошла ошибка взаимодействия: " + e.getMessage());
        return Mono.error(e);
    }
}
