package ru.yandex.practicum.accounts.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.accounts.domain.Account;
import ru.yandex.practicum.accounts.repository.AccountRepository;

import java.math.BigDecimal;

@Component
public class SumService {
    private final AccountRepository accountRepository;

    public SumService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public BigDecimal updateSum(String login, BigDecimal value, String action) {
        Account account = accountRepository.findByLogin(login);

        if ("GET".equals(action) && account.getAmount().compareTo(value) < 0) {
            System.out.println("Недостаточно средств");
            return null;
        }
        BigDecimal newAmount = "GET".equals(action) ? account.getAmount().subtract(value) : account.getAmount().add(value) ;
        account.setAmount(newAmount);
        accountRepository.save(account);

        return account.getAmount();
    }
}
