package com.github.gustavoflor.pockafka.api;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final OutputStream outputStream;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody final Credentials credentials) {
        log.info("Accepted a login request with {}", credentials.getName());
        if (hasTeapot()) {
            handleLoginSuccess(credentials.getName());
            log.info("Login Successful");
            return ResponseEntity.ok().build();
        }
        handleLoginFailure(credentials.getName());
        log.info("Login Failure");
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/logout")
    public void logout() {
        log.info("Accepted a logout request");
        handleLogout();
        log.info("Logout Successful");
    }

    private boolean hasTeapot() {
        return new Random().nextBoolean();
    }

    private void handleLoginSuccess(final String name) {
        outputStream.loginSuccessOutput().send(createMessage(String.format("Hello %s!!!", name)));
    }

    private void handleLoginFailure(final String name) {
        outputStream.loginFailureOutput().send(createMessage(String.format("Oh no, %s are not logged...", name)));
    }

    private void handleLogout() {
        outputStream.logoutOutput().send(createMessage("Bye :(("));
    }

    private Message<String> createMessage(final String payload) {
        return MessageBuilder.withPayload(payload)
            .setHeader("correlationId", getCorrelationId())
            .setHeader("device", hasTeapot() ? "web" : "mobile")
            .build();
    }

    private byte[] getCorrelationId() {
        return UUID.randomUUID().toString().getBytes();
    }

}
