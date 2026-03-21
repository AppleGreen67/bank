package ru.yandex.practicum.accounts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.accounts.service.AccountsService;
import ru.yandex.server.domain.UserAccountSmall;

import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountsService accountService;

    public AccountsController(AccountsService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') && hasAuthority('transfer.write')")
    public ResponseEntity<List<UserAccountSmall>> accountsGet(JwtAuthenticationToken authentication) {

        String login = authentication.getToken().getClaimAsString("preferred_username");
        System.out.println("login: " + login);

        List<UserAccountSmall> userAccounts = accountService.getAccounts();

        if (userAccounts.isEmpty()) {
            System.out.println("ERROR: не нашли пользователей ");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userAccounts);
    }
}
