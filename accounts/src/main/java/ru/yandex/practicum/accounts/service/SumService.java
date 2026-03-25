package ru.yandex.practicum.accounts.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.accounts.domain.Account;
import ru.yandex.practicum.accounts.repository.AccountRepository;

@Component
public class SumService {
    private final AccountRepository accountRepository;

    public SumService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Integer updateSum(String login, Integer value, String action) {
        Account account = accountRepository.findByLogin(login);

        if ("GET".equals(action) && account.getAmount() < value) {
            System.out.println("Недостаточно средств");
            return null;
        }
        Integer newAmount = "GET".equals(action) ? account.getAmount() - value : account.getAmount() + value;
        account.setAmount(newAmount);
        accountRepository.save(account);

        return account.getAmount();
    }
}
