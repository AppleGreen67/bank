package ru.yandex.practicum.mybankfront.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import ru.yandex.practicum.mybankfront.controller.dto.AccountDto;
import ru.yandex.practicum.mybankfront.controller.dto.CashAction;
import ru.yandex.practicum.mybankfront.controller.dto.ModelDto;
import ru.yandex.practicum.mybankfront.exception.BankException;
import ru.yandex.server.domain.UserAccount;
import ru.yandex.server.domain.UserAccountSmall;

import java.util.List;

@Component
public class ModelService {

    private final List<AccountDto> accounts = List.of(
            new AccountDto("petrov", "Петров Петр"),
            new AccountDto("sidorov", "Сидоров Сидор")
    );

    public ModelDto createModel(UserAccount currentAccount, List<UserAccountSmall> accounts) throws BankException {
        ModelDto modelDto = new ModelDto();
        modelDto.setLogin(currentAccount.getLogin());
        modelDto.setName(currentAccount.getName());
        modelDto.setSum(currentAccount.getSum());
        modelDto.setBirthdate(currentAccount.getBirthdate());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = ((OidcUser) authentication.getPrincipal()).getPreferredUsername();

        if (accounts.isEmpty()){
            throw new BankException("Ошибка заполенения accounts в model");
        } else {
            List<AccountDto> accountDtoList = accounts.stream()
                    .filter(account -> !account.getLogin().equalsIgnoreCase(currentUsername))
                    .map(account -> new AccountDto(account.getLogin(), account.getName()))
                    .toList();
            modelDto.setAccounts(accountDtoList);
        }

        return modelDto;
    }

    public ModelDto createModelForUpdateCash(UserAccount account, List<UserAccountSmall> accounts, boolean updateCashResult,
                                             int value, CashAction action) throws BankException {
        ModelDto modelDto = createModel(account, accounts);

        if (updateCashResult) {
            modelDto.setMessage(action == CashAction.GET ? "Снято %d руб".formatted(value) : "Положено %d руб".formatted(value));
        } else {
            modelDto.setErrors(List.of("Недостаточно средств на счету"));
        }

        return modelDto;
    }

    public ModelDto createModelForTransfer(UserAccount account, List<UserAccountSmall> accounts, boolean transferResult,
                                           int value, String toLogin) throws BankException {
        ModelDto modelDto = createModel(account, accounts);

        if (transferResult) {
            modelDto.setMessage("Успешно переведено %d руб клиенту %s".formatted(value, getByLogin(accounts, toLogin)));
        } else {
            modelDto.setErrors(List.of("Недостаточно средств на счету"));
        }

        return modelDto;
    }

    public String getByLogin(List<UserAccountSmall> accounts, String toLogin) {
        return accounts.stream()
                .filter(account -> account.getLogin().equals(toLogin))
                .map(UserAccountSmall::getName)
                .findFirst()
                .get();
    }

    public void fillModel(Model model, ModelDto modelDto) {
        model.addAttribute("name", modelDto.getName());
        model.addAttribute("birthdate", modelDto.getBirthdate());
        model.addAttribute("sum", modelDto.getSum());
        model.addAttribute("accounts", modelDto.getAccounts());
        model.addAttribute("errors", modelDto.getErrors());
        model.addAttribute("info", modelDto.getMessage());
    }



}
