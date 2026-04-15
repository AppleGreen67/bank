package ru.yandex.practicum.accounts.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.accounts.domain.Account;
import ru.yandex.server.domain.UserAccount;
import ru.yandex.server.domain.UserAccountSmall;

@Component
public class AccountMapper {

    public UserAccount mapToUA(Account account) {
        if (account == null) return null;

        UserAccount userAccount = new UserAccount();
        userAccount.setLogin(account.getLogin());
        userAccount.setName(account.getName());
        userAccount.setBirthdate(account.getBirthdate().toString());
        userAccount.setSum(account.getAmount());
        return userAccount;
    }

    public UserAccountSmall mapToUAS(Account account) {
        if (account == null) return null;

        UserAccountSmall userAccount = new UserAccountSmall();
        userAccount.setLogin(account.getLogin());
        userAccount.setName(account.getName());
        return userAccount;
    }
}
