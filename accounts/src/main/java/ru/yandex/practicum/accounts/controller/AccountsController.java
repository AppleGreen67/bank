package ru.yandex.practicum.accounts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.accounts.service.AccountsService;
import ru.yandex.server.domain.UserAccount;
import ru.yandex.server.domain.UserAccountSmall;

import java.util.List;

@Controller
public class AccountsController {

    private final AccountsService accountService;

    public AccountsController(AccountsService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account")
    @PreAuthorize("hasRole('USER') && hasAuthority('transfer.write')")
    public ResponseEntity<UserAccount> accountGet(JwtAuthenticationToken authentication) {

        String login = authentication.getToken().getClaimAsString("preferred_username");
        System.out.println("login: " + login);

        UserAccount userAccount = accountService.getAccount(login);

        if (userAccount == null) {
            System.out.println("ERROR: не нашли пользователя по логину login=" + login);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userAccount);
    }

    @PostMapping("/account")
    @PreAuthorize("hasRole('USER') && hasAuthority('transfer.write')")
    public ResponseEntity<UserAccount> accountPost(@RequestBody UserAccount userAccount, JwtAuthenticationToken authentication) {

        String login = authentication.getToken().getClaimAsString("preferred_username");
        System.out.println("login: " + login);

        UserAccount updatedAccount = accountService.updateAccount(login, userAccount);

        if (updatedAccount == null) {
            System.out.println("ERROR: не нашли пользователя по логину login=" + login);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(updatedAccount);
    }

    @GetMapping("/accounts")
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
