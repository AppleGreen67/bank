package ru.yandex.practicum.accounts.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.accounts.domain.Account;
import ru.yandex.practicum.accounts.repository.AccountRepository;
import ru.yandex.practicum.notification.client.NotificationProducer;

import java.math.BigDecimal;

@Component
public class SumService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SumService.class);

    private final AccountRepository accountRepository;

    public SumService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public BigDecimal updateSum(String login, BigDecimal value, String action) {
        Account account = accountRepository.findByLogin(login);

        if ("GET".equals(action) && account.getAmount().compareTo(value) < 0) {
            LOGGER.error("У клиента {} недостаточно средств", login);
            return null;
        }
        BigDecimal newAmount = "GET".equals(action) ? account.getAmount().subtract(value) : account.getAmount().add(value) ;
        account.setAmount(newAmount);
        accountRepository.save(account);

        return account.getAmount();
    }
}
