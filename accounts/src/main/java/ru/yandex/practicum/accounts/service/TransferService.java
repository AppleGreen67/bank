package ru.yandex.practicum.accounts.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.accounts.domain.Account;
import ru.yandex.practicum.accounts.repository.AccountRepository;

@Component
public class TransferService {

    private final AccountRepository accountRepository;

    public TransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Integer transfer(String fromLogin, Integer value, String toLogin) {
        Account fromAccount = accountRepository.findByLogin(fromLogin);

        if (fromAccount.getAmount() < value) {
            System.out.println("Недостаточно средств");
            return null;
        }

        Account toAccount = accountRepository.findByLogin(toLogin);
        toAccount.setAmount(toAccount.getAmount() + value);

        fromAccount.setAmount(fromAccount.getAmount() - value);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        return fromAccount.getAmount();
    }
}
