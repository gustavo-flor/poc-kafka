package com.github.gustavoflor.pockafka.worker;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AuthListener {

    private final Random random;

    @StreamListener(value = InputStream.LOGIN_SUCCESS, condition = "'mobile' eq headers['device']")
    public void mobileLoginSuccessListener(@Payload final Credentials credentials,
                                           @Header(required = false, name = "correlationId") final byte[] correlationIdHeader,
                                           @Header(required = false, name = "partitionKey") final String partitionKey) {
        final UUID correlationId = UUID.fromString(new String(correlationIdHeader));
        log("Mobile Login Success", correlationId, credentials, partitionKey);
    }

    @StreamListener(value = InputStream.LOGIN_SUCCESS, condition = "'web' eq headers['device']")
    public void webLoginSuccessListener(@Payload final Credentials credentials,
                                        @Header(required = false, name = "correlationId") final byte[] correlationIdHeader,
                                        @Header(required = false, name = "partitionKey") final String partitionKey) {
        final UUID correlationId = UUID.fromString(new String(correlationIdHeader));
        log("Web Login Success", correlationId, credentials, partitionKey);
    }

    @StreamListener(InputStream.LOGIN_FAILURE)
    public void loginFailureListener(@Payload final Credentials credentials,
                                     @Header(required = false, name = "correlationId") final byte[] correlationIdHeader,
                                     @Header(required = false, name = "partitionKey") final String partitionKey) {
        final UUID correlationId = UUID.fromString(new String(correlationIdHeader));
        log("Login Failure", correlationId, credentials, partitionKey);
    }

    @StreamListener(InputStream.LOGOUT)
    public void logoutListener(@Payload final Credentials credentials,
                               @Header(required = false, name = "correlationId") final byte[] correlationIdHeader,
                               @Header(required = false, name = "deliveryAttempt") final AtomicInteger deliveryAttempt) {
        final UUID correlationId = UUID.fromString(new String(correlationIdHeader));
        if (hasTeapot()) {
            log("Logout, failure on listen with attempts: " + deliveryAttempt.get(), correlationId, credentials, "HAS NOT PARTITION KEY");
            throw new WorkerException();
        }
        log("Logout, success on listen with attempts: " + deliveryAttempt.get(), correlationId, credentials, "HAS NOT PARTITION KEY");
    }

    private void log(final String context , final UUID correlationId, final Credentials credentials, final String partitionKey) {
        log.info("Listened a event of {}", context);
        log.info("Correlation ID: {}", correlationId);
        log.info("Payload: {}", credentials);
        log.info("Partition Key: {}", partitionKey);
    }

    private boolean hasTeapot() {
        return random.nextBoolean();
    }

}
