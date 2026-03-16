package ru.yandex.practicum.accounts.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.accounts.domain.Account;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    @Override
    List<Account> findAll();

    Account findByLogin(String login);
}
