package com.github.gustavoflor.pockafka.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final OutputStream outputStream;
    private final Random random;

    public boolean login(String name) {
        if (hasTeapot()) {
            outputStream.loginSuccessOutput().send(createMessage(String.format(OutputStream.LOGIN_SUCCESS_MESSAGE, name), name));
            log.info("Login Successful");
            return true;
        }

        outputStream.loginFailureOutput().send(createMessage(String.format(OutputStream.LOGIN_FAILURE_MESSAGE, name), name));
        log.info("Login Failure");
        return false;
    }

    public void logout() {
        outputStream.logoutOutput().send(createMessage(OutputStream.LOGOUT_MESSAGE, null));
        log.info("Logout Successful");
    }

    private Message<String> createMessage(final String payload, final String partitionKey) {
        final Map<String, Object> headers = new HashMap<>();
        headers.put("correlationId", getCorrelationId());
        headers.put("device", hasTeapot() ? "web" : "mobile");
        headers.put("partitionKey", partitionKey);

        return MessageBuilder.createMessage(payload, new MessageHeaders(headers));
    }

    private boolean hasTeapot() {
        return random.nextBoolean();
    }

    private byte[] getCorrelationId() {
        return UUID.randomUUID().toString().getBytes();
    }

}
