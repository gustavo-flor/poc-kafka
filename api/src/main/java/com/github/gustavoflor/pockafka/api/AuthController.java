package com.github.gustavoflor.pockafka.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody final Credentials credentials) {
        log.info("Accepted a login request with {}", credentials.getName());
        return authService.login(credentials.getName())
                ? ResponseEntity.ok().build()
                :  ResponseEntity.badRequest().build();
    }

    @PostMapping("/logout")
    public void logout() {
        log.info("Accepted a logout request");
        authService.logout();
    }



}
