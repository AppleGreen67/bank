package ru.yandex.practicum.mybankfront.service;

import jakarta.annotation.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import ru.yandex.practicum.mybankfront.controller.dto.AccountDto;
import ru.yandex.practicum.mybankfront.controller.dto.ModelDto;
import ru.yandex.server.domain.UserAccount;
import ru.yandex.server.domain.UserAccountSmall;

import java.util.List;

@Component
public class ModelService {

    private final List<AccountDto> accounts = List.of(
            new AccountDto("petrov", "Петров Петр"),
            new AccountDto("sidorov", "Сидоров Сидор")
    );

    public ModelDto createModel(UserAccount currentAccount, List<UserAccountSmall> accounts) {
        ModelDto modelDto = new ModelDto();
        modelDto.setLogin(currentAccount.getLogin());
        modelDto.setName(currentAccount.getName());
        modelDto.setBirthdate(currentAccount.getBirthdate());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = ((OidcUser) authentication.getPrincipal()).getPreferredUsername();

        if (accounts.isEmpty()){
            //todo ??
        } else {
            List<AccountDto> accountDtoList = accounts.stream()
                    .filter(account -> !account.getLogin().equalsIgnoreCase(currentUsername))
                    .map(account -> new AccountDto(account.getLogin(), account.getName()))
                    .toList();
            modelDto.setAccounts(accountDtoList);
        }

        return modelDto;
    }

    public void fillModel(Model model, ModelDto account,
                          @Nullable List<String> errors, @Nullable String info) {
        model.addAttribute("name", account.getName());
        model.addAttribute("birthdate", account.getBirthdate());
        model.addAttribute("sum", account.getSum());
        model.addAttribute("accounts", account.getAccounts());
        model.addAttribute("errors", errors);
        model.addAttribute("info", info);
    }
}
