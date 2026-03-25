package ru.yandex.practicum.accounts.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.accounts.domain.Account;
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

        return mapToUA(account);
    }

    private UserAccount mapToUA(Account account) {
        if (account == null) return null;

        UserAccount userAccount = new UserAccount();
        userAccount.setLogin(account.getLogin());
        userAccount.setName(account.getName());
        userAccount.setBirthdate(account.getBirthdate().toString());
        userAccount.setSum(account.getAmount());
        return userAccount;
    }

    public List<UserAccountSmall> getAccounts() {
        List<Account> accounts = accountRepository.findAll();

        return accounts.stream()
                .map(this::mapToUAS)
                .filter(Objects::nonNull)
                .toList();
    }

    private UserAccountSmall mapToUAS(Account account) {
        if (account == null) return null;

        UserAccountSmall userAccount = new UserAccountSmall();
        userAccount.setLogin(account.getLogin());
        userAccount.setName(account.getName());
        return userAccount;
    }


    public UserAccount updateAccount(String login, UserAccount updatedAccount) {
        Account accountFromDB = accountRepository.findByLogin(login);

        if (accountFromDB == null) return null;

        accountFromDB.setName(updatedAccount.getName());
        if (updatedAccount.getBirthdate() != null)
            accountFromDB.setBirthdate(LocalDate.parse(updatedAccount.getBirthdate()));

        accountRepository.save(accountFromDB);

        return mapToUA(accountFromDB);
    }
}
