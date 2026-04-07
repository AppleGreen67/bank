package ru.yandex.practicum.accounts.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.accounts.domain.Account;
import ru.yandex.practicum.accounts.repository.AccountRepository;

import java.math.BigDecimal;

@Component
public class TransferService {

    private final AccountRepository accountRepository;

    public TransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public BigDecimal transfer(String fromLogin, BigDecimal value, String toLogin) {
        Account fromAccount = accountRepository.findByLogin(fromLogin);

        if (fromAccount.getAmount().compareTo(value) < 0) {
            System.out.println("Недостаточно средств");
            return null;
        }

        Account toAccount = accountRepository.findByLogin(toLogin);
        toAccount.setAmount(toAccount.getAmount().add(value));

        fromAccount.setAmount(fromAccount.getAmount().subtract(value));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        return fromAccount.getAmount();
    }
}
