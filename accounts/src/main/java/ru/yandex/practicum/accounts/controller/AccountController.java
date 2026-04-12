package ru.yandex.practicum.accounts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.accounts.service.AccountsService;
import ru.yandex.practicum.accounts.service.NotificationService;
import ru.yandex.practicum.accounts.service.SumService;
import ru.yandex.practicum.accounts.service.TransferService;
import ru.yandex.practicum.accounts.service.UserService;
import ru.yandex.server.domain.CashRequest;
import ru.yandex.server.domain.TransferRequest;
import ru.yandex.server.domain.UserAccount;

import java.math.BigDecimal;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountsService accountService;
    private final SumService sumService;
    private final TransferService transferService;
    private final NotificationService notificationService;
    private final UserService userService;

    public AccountController(AccountsService accountService, SumService sumService, TransferService transferService,
                             NotificationService notificationService, UserService userService) {
        this.accountService = accountService;
        this.sumService = sumService;
        this.transferService = transferService;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') && hasAuthority('transfer.write')")
    public ResponseEntity<UserAccount> getAccount(JwtAuthenticationToken authentication) {
        String login = userService.getCurrentLogin(authentication);

        notificationService.sendMessage("Запрос аккаунта " + login, false);

        UserAccount userAccount = accountService.getAccount(login);

        if (userAccount == null) {
            notificationService.sendMessage("Аккаунт " + login + " не найден", true);
            return ResponseEntity.notFound().build();
        }

        notificationService.sendMessage("Аккаунт " + login + " найден", false);
        return ResponseEntity.ok().body(userAccount);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') && hasAuthority('transfer.write')")
    public ResponseEntity<UserAccount> updateAccount(@RequestBody UserAccount userAccount, JwtAuthenticationToken authentication) {
        String login = userService.getCurrentLogin(authentication);

        notificationService.sendMessage("Запрос обновления аккаунта " + login, false);

        UserAccount updatedAccount = accountService.updateAccount(login, userAccount);

        if (updatedAccount == null) {
            notificationService.sendMessage("Ошибка обновления аккаунта " + login, true);
            return ResponseEntity.notFound().build();
        }

        notificationService.sendMessage("Аккаунт " + login + " успешно обновлен", false);
        return ResponseEntity.ok().body(updatedAccount);
    }

    @PostMapping("/{login}/change")
    @PreAuthorize("hasRole('SERVICE') && hasAuthority('accounts.write')")
    public ResponseEntity<BigDecimal> change(@PathVariable String login, @RequestBody CashRequest request,
                                             JwtAuthenticationToken authentication) {

        notificationService.sendMessage("Запрос изменения счета аккаунта " + login, false);

        BigDecimal amount = sumService.updateSum(login, request.getSum(), request.getAction());

        if (amount == null) {
            notificationService.sendMessage("Ошибка изменения счета аккаунта " + login, true);
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();
        }

        notificationService.sendMessage("Счет аккаунта " + login + " успешно обновлен", false);
        return ResponseEntity.ok().body(amount);
    }

    @PostMapping("/{login}/transfer")
    @PreAuthorize("hasRole('SERVICE') && hasAuthority('accounts.write')")
    public ResponseEntity<BigDecimal> transfer(@PathVariable String login, @RequestBody TransferRequest request,
                                            JwtAuthenticationToken authentication) {
        notificationService.sendMessage("Запрос на перевод средств с аккаунта " + login + " аккаунту " + request.getLogin(), false);

        BigDecimal amount = transferService.transfer(login, request.getSum(), request.getLogin());

        if (amount == null) {
            notificationService.sendMessage("Ошибка перевода средств с аккаунта " + login + " аккаунту " + request.getLogin(), true);
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();
        }

        notificationService.sendMessage("Перевод средств с аккаунта " + login + " аккаунту " + request.getLogin() + " успешно проведен", false);
        return ResponseEntity.ok().body(amount);
    }
}
