package ru.yandex.practicum.cash.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.cash.service.AccountsService;
import ru.yandex.practicum.cash.service.NotificationService;
import ru.yandex.server.domain.CashRequest;

@Controller
public class CashController {
    private final AccountsService accountsService;
    private final NotificationService notificationService;

    public CashController(AccountsService accountsService, NotificationService notificationService) {
        this.accountsService = accountsService;
        this.notificationService = notificationService;
    }

    @PostMapping("/cash")
    @PreAuthorize("hasRole('USER') && hasAuthority('transfer.write')")
    public ResponseEntity<Integer> cashPost(@RequestBody CashRequest request, JwtAuthenticationToken authentication) {
        String login = authentication.getToken().getClaimAsString("preferred_username");

        notificationService.sendMessage(login,"Запрос изменения счета аккаунта " + login, false);

        Integer sum = accountsService.updateAccount(request, login);

        notificationService.sendMessage(login,"Счет аккаунта " + login + " успешно обновлен", false);
        return ResponseEntity.ok().body(sum);
    }
}
