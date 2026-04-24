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
    private final AccountMapper accountMapper;

    public AccountsService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public UserAccount getAccount(String login) {
        Account account = accountRepository.findByLogin(login);

        return accountMapper.mapToUA(account);
    }

    public List<UserAccountSmall> getAccounts() {
        List<Account> accounts = accountRepository.findAll();

        return accounts.stream()
                .map(accountMapper::mapToUAS)
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

        return accountMapper.mapToUA(accountFromDB);
    }
}
