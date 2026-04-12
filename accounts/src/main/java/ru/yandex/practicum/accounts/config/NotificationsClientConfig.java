//package ru.yandex.practicum.accounts.config;
//
//import io.github.resilience4j.circuitbreaker.CircuitBreaker;
//import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
//import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
//import io.github.resilience4j.timelimiter.TimeLimiter;
//import io.github.resilience4j.timelimiter.TimeLimiterConfig;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.time.Duration;
//
//@Configuration
//public class NotificationsClientConfig {
//
//    @Bean
//    public CircuitBreakerConfig circuitBreakerConfig() {
//        return CircuitBreakerConfig.custom()
//                .failureRateThreshold(50)
//                .minimumNumberOfCalls(10)
//                .slidingWindowSize(10)
//                .waitDurationInOpenState(Duration.ofSeconds(10))
//                .permittedNumberOfCallsInHalfOpenState(5)
//                .slowCallDurationThreshold(Duration.ofSeconds(4))
//                .slowCallRateThreshold(50)
//                .recordException(throwable -> true)
//                .build();
//    }
//
//    @Bean
//    public CircuitBreaker notificationCircuitBreaker(CircuitBreakerConfig config) {
//        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
//        return registry.circuitBreaker("notificationService");
//    }
//
//    @Bean
//    public TimeLimiter timeLimiter() {
//        TimeLimiterConfig config = TimeLimiterConfig.custom()
//                .timeoutDuration(Duration.ofSeconds(5))
//                .build();
//
//        return TimeLimiter.of(config);
//    }
//
//    @Bean
//    public OAuth2AuthorizedClientManager authorizedClientManager(ClientRegistrationRepository clientRegistrationRepository,
//                                                                 OAuth2AuthorizedClientService authorizedClientService) {
//        OAuth2AuthorizedClientProvider authorizedClientProvider =
//                OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build();
//
//        AuthorizedClientServiceOAuth2AuthorizedClientManager manager =
//                new AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientService);
//
//        manager.setAuthorizedClientProvider(authorizedClientProvider);
//        return manager;
//    }
//
//    @Bean
//    @LoadBalanced
//    public WebClient.Builder loadBalancedWebClientBuilder() {
//        return WebClient.builder();
//    }
//
//    @Bean
//    public WebClient notificationWebClient(WebClient.Builder builder, OAuth2AuthorizedClientManager authorizedClientManager) {
//        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
//                new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
//
//        oauth2.setDefaultClientRegistrationId("transfer-service");
//
//        return builder
//                .apply(oauth2.oauth2Configuration())
//                .build();
//    }
//}
