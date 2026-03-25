package ru.yandex.practicum.accounts.service;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    public String getCurrentLogin(JwtAuthenticationToken authentication) {
        return authentication.getToken().getClaimAsString("preferred_username");
    }
}
