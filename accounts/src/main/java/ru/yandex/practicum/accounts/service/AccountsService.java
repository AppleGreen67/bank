package ru.yandex.practicum.accounts.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.accounts.domain.Account;
import ru.yandex.practicum.accounts.mapper.AccountMapper;
import ru.yandex.practicum.accounts.repository.AccountRepository;
import ru.yandex.server.domain.UserAccount;
import ru.yandex.server.domain.UserAccountSmall;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
public class AccountsService {
    private final AccountRepository accountRepository;

    public AccountsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public UserAccount getAccount(String login) {
        Account account = accountRepository.findByLogin(login);

        return AccountMapper.mapToUA(account);
    }

    public List<UserAccountSmall> getAccounts() {
        List<Account> accounts = accountRepository.findAll();

        return accounts.stream()
                .map(AccountMapper::mapToUAS)
                .filter(Objects::nonNull)
                .toList();
    }

    public UserAccount updateAccount(String login, UserAccount updatedAccount) {
        Account accountFromDB = accountRepository.findByLogin(login);

        if (accountFromDB == null) return null;

        accountFromDB.setName(updatedAccount.getName());
        if (updatedAccount.getBirthdate() != null)
            accountFromDB.setBirthdate(LocalDate.parse(updatedAccount.getBirthdate()));

        accountRepository.save(accountFromDB);

        return AccountMapper.mapToUA(accountFromDB);
    }
}
