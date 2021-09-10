package com.github.gustavoflor.pockafka.worker;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface InputStream {

    String LOGIN_SUCCESS = "poc-kafka-login-success-input";
    String LOGIN_FAILURE = "poc-kafka-login-failure-input";
    String LOGOUT = "poc-kafka-logout-input";

    @Input(InputStream.LOGIN_SUCCESS)
    SubscribableChannel loginSuccessInput();

    @Input(InputStream.LOGIN_FAILURE)
    SubscribableChannel loginFailureInput();

    @Input(InputStream.LOGOUT)
    SubscribableChannel logoutInput();
}
