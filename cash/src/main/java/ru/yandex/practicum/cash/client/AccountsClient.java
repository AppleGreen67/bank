package ru.yandex.practicum.cash.client;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.yandex.server.domain.CashRequest;

import java.time.Duration;

@Component
public class AccountsClient {
    private final WebClient webClient;

    @Value("${bank.accounts.base-url}")
    private String accountsBaseUrl;
    @Value("${bank.accounts.timeout}")
    private Long accountsTimeout;

    private final CircuitBreaker circuitBreaker;
    private final TimeLimiter timeLimiter;

    public AccountsClient(WebClient webClient, @Qualifier("accountCircuitBreaker") CircuitBreaker circuitBreaker,
                          TimeLimiter timeLimiter) {
        this.webClient = webClient;
        this.circuitBreaker = circuitBreaker;
        this.timeLimiter = timeLimiter;
    }

    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "accountService", fallbackMethod = "updateSumFallback")
    @io.github.resilience4j.retry.annotation.Retry(name = "accountService", fallbackMethod = "updateSumFallback")
    public Integer updateSum(CashRequest request, String login) {
        return webClient
                .post()
                .uri(accountsBaseUrl + "/account/{login}/change", login)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Integer.class)
                .timeout(Duration.ofSeconds(accountsTimeout))
                .block();
    }

    private Mono<Integer> updateSumFallback(CashRequest request, String login, Exception e) {
        System.err.println("Circuit breaker открыт или произошла ошибка взаимодействия: " + e.getMessage());
        return Mono.error(e);
    }
}
