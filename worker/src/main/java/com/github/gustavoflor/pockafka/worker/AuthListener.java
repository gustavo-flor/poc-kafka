package com.github.gustavoflor.pockafka.worker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class AuthListener {

    @StreamListener(value = InputStream.LOGIN_SUCCESS, condition = "'mobile' eq headers['device']")
    public void mobileLoginSuccessListener(@Payload final String payload,
                                           @Header(required = false, name = "correlationId") final byte[] correlationIdHeader,
                                           @Header(required = false, name = "partitionKey") final String partitionKey) {
        final UUID correlationId = UUID.fromString(new String(correlationIdHeader));
        log("Mobile Login Success", correlationId, payload, partitionKey);
    }

    @StreamListener(value = InputStream.LOGIN_SUCCESS, condition = "'web' eq headers['device']")
    public void webLoginSuccessListener(@Payload final String payload,
                                        @Header(required = false, name = "correlationId") final byte[] correlationIdHeader,
                                        @Header(required = false, name = "partitionKey") final String partitionKey) {
        final UUID correlationId = UUID.fromString(new String(correlationIdHeader));
        log("Web Login Success", correlationId, payload, partitionKey);
    }

    @StreamListener(InputStream.LOGIN_FAILURE)
    public void loginFailureListener(@Payload final String payload,
                                     @Header(required = false, name = "correlationId") final byte[] correlationIdHeader,
                                     @Header(required = false, name = "partitionKey") final String partitionKey) {
        final UUID correlationId = UUID.fromString(new String(correlationIdHeader));
        log("Login Failure", correlationId, payload, partitionKey);
    }

    @StreamListener(InputStream.LOGOUT)
    public void logoutListener(@Payload final String payload,
                               @Header(required = false, name = "correlationId") final byte[] correlationIdHeader,
                               @Header(required = false, name = "deliveryAttempt") final AtomicInteger deliveryAttempt) {
        final UUID correlationId = UUID.fromString(new String(correlationIdHeader));
        if (hasTeapot()) {
            log("Logout with Failure, attempts: " + deliveryAttempt.get(), correlationId, payload, "HAS NOT PARTITION KEY");
            throw new WorkerException();
        }
        log("Logout with Success, attempts: " + deliveryAttempt.get(), correlationId, payload, "HAS NOT PARTITION KEY");
    }

    private void log(final String context , final UUID correlationId, final String payload, final String partitionKey) {
        log.info("Listened a event of {}", context);
        log.info("Correlation ID: {}", correlationId);
        log.info("Payload: {}", payload);
        log.info("Partition Key: {}", partitionKey);
    }

    private boolean hasTeapot() {
        return new Random().nextBoolean();
    }

}
