package ru.yandex.practicum.accounts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.accounts.service.AccountsService;
import ru.yandex.practicum.accounts.service.NotificationService;
import ru.yandex.server.domain.UserAccountSmall;

import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountsService accountService;
    private final NotificationService notificationService;

    public AccountsController(AccountsService accountService, NotificationService notificationService) {
        this.accountService = accountService;
        this.notificationService = notificationService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') && hasAuthority('transfer.write')")
    public ResponseEntity<List<UserAccountSmall>> accountsGet(JwtAuthenticationToken authentication) {
        notificationService.sendMessage("Запрос аккаунтов", false);

        List<UserAccountSmall> userAccounts = accountService.getAccounts();

        if (userAccounts.isEmpty()) {
            notificationService.sendMessage("Аккаунты не найдены", false);
            return ResponseEntity.notFound().build();
        }

        notificationService.sendMessage("Аккаунты успешно найдены", false);
        return ResponseEntity.ok().body(userAccounts);
    }
}
