package ru.yandex.practicum.transfer.client;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.yandex.server.domain.CashRequest;
import ru.yandex.server.domain.TransferRequest;

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
    public Integer updateSum(TransferRequest request, String login) {
        return webClient
                .post()
                .uri(accountsBaseUrl + "/account/{login}/transfer", login)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
    }

    private Mono<Integer> updateSumFallback(CashRequest request, String login, Exception e) {
        System.err.println("Circuit breaker открыт или произошла ошибка взаимодействия: " + e.getMessage());
        return Mono.error(e);
    }
}
