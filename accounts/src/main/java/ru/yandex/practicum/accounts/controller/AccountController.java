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
import ru.yandex.practicum.accounts.service.SumService;
import ru.yandex.practicum.accounts.service.TransferService;
import ru.yandex.server.domain.CashRequest;
import ru.yandex.server.domain.TransferRequest;
import ru.yandex.server.domain.UserAccount;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountsService accountService;
    private final SumService sumService;
    private final TransferService transferService;

    public AccountController(AccountsService accountService, SumService sumService, TransferService transferServiceService) {
        this.accountService = accountService;
        this.sumService = sumService;
        this.transferService = transferServiceService;
    }

    @GetMapping
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

    @PostMapping
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

    @PostMapping("/{login}/change")
    @PreAuthorize("hasRole('SERVICE') && hasAuthority('accounts.write')")
    public ResponseEntity<Integer> updateSum(@PathVariable String login, @RequestBody CashRequest request,
                                             JwtAuthenticationToken authentication) {
        System.out.println("account login: " + login);
        System.out.println("account summ: " + request.getSum());
        System.out.println("account action: " + request.getAction());

        Integer amount = sumService.updateSum(login, request.getSum(), request.getAction());

        if (amount == null) {
            System.out.println("ERROR: недостаточно средств пользователя по логину login=" + login);
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();
        }
        return ResponseEntity.ok().body(amount);
    }

    @PostMapping("/{login}/transfer")
    @PreAuthorize("hasRole('SERVICE') && hasAuthority('accounts.write')")
    public ResponseEntity<Integer> transfer(@PathVariable String login, @RequestBody TransferRequest request,
                                             JwtAuthenticationToken authentication) {
        System.out.println("account current login: " + login);
        System.out.println("account summ: " + request.getSum());
        System.out.println("account to login: " + request.getLogin());

        Integer amount = transferService.transfer(login, request.getSum(), request.getLogin());

        if (amount == null) {
            System.out.println("ERROR: недостаточно средств пользователя по логину login=" + login);
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();
        }
        return ResponseEntity.ok().body(amount);
    }
}
