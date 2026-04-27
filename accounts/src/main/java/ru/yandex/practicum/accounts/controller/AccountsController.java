package ru.yandex.practicum.accounts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.accounts.service.AccountsService;
import ru.yandex.practicum.accounts.service.NotificationService;
import ru.yandex.practicum.accounts.service.UserService;
import ru.yandex.server.domain.UserAccountSmall;

import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountsService accountService;
    private final NotificationService notificationService;
    private final UserService userService;

    public AccountsController(AccountsService accountService, NotificationService notificationService, UserService userService) {
        this.accountService = accountService;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') && hasAuthority('transfer.write')")
    public ResponseEntity<List<UserAccountSmall>> accountsGet(JwtAuthenticationToken authentication) {
        String login = userService.getCurrentLogin(authentication);

        notificationService.sendMessage(login,"Запрос аккаунтов", false);

        List<UserAccountSmall> userAccounts = accountService.getAccounts();

        if (userAccounts.isEmpty()) {
            notificationService.sendMessage(login,"Аккаунты не найдены", false);
            return ResponseEntity.notFound().build();
        }

        notificationService.sendMessage(login,"Аккаунты успешно найдены", false);
        return ResponseEntity.ok().body(userAccounts);
    }
}
