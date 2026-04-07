package ru.yandex.practicum.mybankfront.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.mybankfront.controller.dto.CashAction;
import ru.yandex.practicum.mybankfront.controller.dto.ModelDto;
import ru.yandex.practicum.mybankfront.exception.BankException;
import ru.yandex.practicum.mybankfront.service.AccountsService;
import ru.yandex.practicum.mybankfront.service.CashService;
import ru.yandex.practicum.mybankfront.service.ModelService;
import ru.yandex.practicum.mybankfront.service.TransferService;
import ru.yandex.server.domain.UserAccount;
import ru.yandex.server.domain.UserAccountSmall;

import java.time.LocalDate;
import java.util.List;

/**
 * Контроллер main.html.
 * <p>
 * Используемая модель для main.html:
 * model.addAttribute("name", name);
 * model.addAttribute("birthdate", birthdate.format(DateTimeFormatter.ISO_DATE));
 * model.addAttribute("sum", sum);
 * model.addAttribute("accounts", accounts);
 * model.addAttribute("errors", errors);
 * model.addAttribute("info", info);
 * <p>
 * Поля модели:
 * name - Фамилия Имя текущего пользователя, String (обязательное)
 * birthdate - дата рождения текущего пользователя, String в формате 'YYYY-MM-DD' (обязательное)
 * sum - сумма на счету текущего пользователя, Integer (обязательное)
 * accounts - список аккаунтов, которым можно перевести деньги, List<AccountDto> (обязательное)
 * errors - список ошибок после выполнения действий, List<String> (не обязательное)
 * info - строка успешности после выполнения действия, String (не обязательное)
 * <p>
 * С примерами использования можно ознакомиться в тестовом классе заглушке AccountStub
 */
@Controller
public class MainController {

    private final AccountsService accountsService;
    private final CashService cashService;
    private final TransferService transferService;
    private final ModelService modelService;

    public MainController(AccountsService accountsService, CashService cashService, TransferService transferService, ModelService modelService) {
        this.accountsService = accountsService;
        this.cashService = cashService;
        this.transferService = transferService;
        this.modelService = modelService;
    }

    /**
     * GET /.
     * Редирект на GET /account
     */
    @GetMapping
    public String index() {
        return "redirect:/account";
    }

    /**
     * GET /account.
     * Что нужно сделать:
     * 1. Сходить в сервис accounts через Gateway API для получения данных аккаунта по REST
     * 2. Заполнить модель main.html полученными из ответа данными
     * 3. Текущего пользователя можно получить из контекста Security
     */
    @GetMapping("/account")
    public String getAccount(Model model) throws BankException {
        UserAccount account = accountsService.getAccount();
        List<UserAccountSmall> accounts = accountsService.getAccounts();
        ModelDto modelDto = modelService.createModel(account, accounts);

        modelService.fillModel(model, modelDto);

        return "main";
    }

    /**
     * POST /account.
     * Что нужно сделать:
     * 1. Сходить в сервис accounts через Gateway API для изменения данных текущего пользователя по REST
     * 2. Заполнить модель main.html полученными из ответа данными
     * 3. Текущего пользователя можно получить из контекста Security
     * <p>
     * Изменяемые данные:
     * 1. name - Фамилия Имя
     * 2. birthdate - дата рождения в формате YYYY-DD-MM
     */
    @PostMapping("/account")
    public String editAccount(Model model, @RequestParam("name") String name, @RequestParam("birthdate") LocalDate birthdate) throws BankException {
        UserAccount account = accountsService.updateAccount(name, birthdate);
        List<UserAccountSmall> accounts = accountsService.getAccounts();
        ModelDto modelDto = modelService.createModel(account, accounts);

        modelService.fillModel(model, modelDto);

        return "main";
    }

    /**
     * POST /cash.
     * Что нужно сделать:
     * 1. Сходить в сервис cash через Gateway API для снятия/пополнения счета текущего аккаунта по REST
     * 2. Заполнить модель main.html полученными из ответа данными
     * 3. Текущего пользователя можно получить из контекста Security
     * <p>
     * Параметры:
     * 1. value - сумма списания
     * 2. action - GET (снять), PUT (пополнить)
     */
    @PostMapping("/cash")
    public String editCash(Model model, @RequestParam("value") int value, @RequestParam("action") CashAction action) throws BankException {
        boolean updateCashResult = cashService.updateCash(value, action);

        UserAccount account = accountsService.getAccount();
        List<UserAccountSmall> accounts = accountsService.getAccounts();
        ModelDto modelDto = modelService.createModelForUpdateCash(account, accounts, updateCashResult, value, action);

        modelService.fillModel(model, modelDto);

        return "main";
    }

    /**
     * POST /transfer.
     * Что нужно сделать:
     * 1. Сходить в сервис accounts через Gateway API для перевода со счета текущего аккаунта на счет другого аккаунта по REST
     * 2. Заполнить модель main.html полученными из ответа данными
     * 3. Текущего пользователя можно получить из контекста Security
     * <p>
     * Параметры:
     * 1. value - сумма списания
     * 2. login - логин пользователя получателя
     */
    @PostMapping("/transfer")
    public String transfer(Model model, @RequestParam("value") int value, @RequestParam("login") String login) throws BankException {
        boolean transferResult = transferService.transfer(value, login);

        UserAccount account = accountsService.getAccount();
        List<UserAccountSmall> accounts = accountsService.getAccounts();
        ModelDto modelDto = modelService.createModelForTransfer(account, accounts, transferResult, value, login);

        modelService.fillModel(model, modelDto);

        return "main";
    }
}
