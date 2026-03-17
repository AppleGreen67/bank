package ru.yandex.practicum.cash.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.cash.service.AccountsService;
import ru.yandex.server.domain.CashRequest;
import ru.yandex.server.domain.UserAccount;

@Controller
public class CashController {
    private final AccountsService accountsService;

    public CashController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @PostMapping("/cash")
    public ResponseEntity<Integer> cashPost(@RequestBody CashRequest request, JwtAuthenticationToken authentication) {
        String login = authentication.getToken().getClaimAsString("preferred_username");
        System.out.println("login: " + login);
        System.out.println("summ: " + request.getSum());
        System.out.println("action: " + request.getAction());

        Integer sum = accountsService.updateAccount(request);

        //todo обработка ошибок

        return ResponseEntity.ok().body(sum);
    }
}
