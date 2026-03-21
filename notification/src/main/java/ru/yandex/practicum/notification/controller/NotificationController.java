package ru.yandex.practicum.notification.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.server.domain.NotifyMessage;

@RestController
@RequestMapping("/notify")
public class NotificationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class.getName());

    @PostMapping
    @PreAuthorize("hasAuthority('notifications')")
    public ResponseEntity<Integer> notify(@RequestBody NotifyMessage request, JwtAuthenticationToken authentication) {

        LOGGER.info("Hello {}", request);

        return ResponseEntity.ok().build();
    }
}
