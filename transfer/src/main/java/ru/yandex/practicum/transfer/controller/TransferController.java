package ru.yandex.practicum.transfer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.transfer.service.TransferToAccountsService;
import ru.yandex.server.domain.TransferRequest;

@Controller
public class TransferController {
    private final TransferToAccountsService service;

    public TransferController(TransferToAccountsService service) {
        this.service = service;
    }

    @PostMapping("/transfer")
    @PreAuthorize("hasRole('USER') && hasAuthority('transfer.write')")
    public ResponseEntity<Integer> cashPost(@RequestBody TransferRequest request, JwtAuthenticationToken authentication) {
        String login = authentication.getToken().getClaimAsString("preferred_username");
        System.out.println("transfer current login: " + login);
        System.out.println("transfer summ: " + request.getSum());
        System.out.println("transfer to login: " + request.getLogin());

        Integer sum = service.transfer(request, login);

        //todo обработка ошибок

        return ResponseEntity.ok().body(sum);
    }
}
