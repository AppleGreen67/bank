package ru.yandex.practicum.accounts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.server.domain.UserAccount;

@Controller
public class AccountsController{

    @GetMapping("/account")
    public ResponseEntity<UserAccount> accountGet(JwtAuthenticationToken authentication) {

        String username = authentication.getToken().getClaimAsString("preferred_username");

        UserAccount userAccount = new UserAccount();
        userAccount.setLogin("ivanov");
        userAccount.setName("Иванов Иван");

        return ResponseEntity.ok().body(userAccount);
    }
}
