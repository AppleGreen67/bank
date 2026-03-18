package ru.yandex.practicum.accounts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.accounts.service.SumService;
import ru.yandex.server.domain.CashRequest;

@Controller
public class SumController {
    private final SumService sumService;

    public SumController(SumService sumService) {
        this.sumService = sumService;
    }

    @PostMapping("{login}/change")
    public ResponseEntity<Integer> updateSum(@PathVariable String login, @RequestBody CashRequest request, JwtAuthenticationToken authentication) {
        System.out.println("login: " + login);
        System.out.println("summ: " + request.getSum());
        System.out.println("action: " + request.getAction());

        Integer amount = sumService.updateSum(request.getSum(), request.getAction(), login);

        if (amount == null) {
            System.out.println("ERROR: недостаточно средств пользователя по логину login=" + login);
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();
        }
        return ResponseEntity.ok().body(amount);
    }
}
