package ru.yandex.practicum.transfer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.transfer.service.NotificationService;
import ru.yandex.practicum.transfer.service.TransferToAccountsService;
import ru.yandex.server.domain.TransferRequest;

@Controller
public class TransferController {
    private final TransferToAccountsService service;
    private final NotificationService notificationService;

    public TransferController(TransferToAccountsService service, NotificationService notificationService) {
        this.service = service;
        this.notificationService = notificationService;
    }

    @PostMapping("/transfer")
    @PreAuthorize("hasRole('USER') && hasAuthority('transfer.write')")
    public ResponseEntity<Integer> cashPost(@RequestBody TransferRequest request, JwtAuthenticationToken authentication) {
        String login = authentication.getToken().getClaimAsString("preferred_username");

        notificationService.sendMessage(login,"Запрос на перевод средств с аккаунта " + login + " аккаунту " + request.getLogin(), false);

        Integer sum = service.transfer(request, login);

        notificationService.sendMessage(login,"Перевод средств с аккаунта " + login + " аккаунту " + request.getLogin() + " успешно проведен", false);

        return ResponseEntity.ok().body(sum);
    }
}
