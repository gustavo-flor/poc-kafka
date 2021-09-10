package com.github.gustavoflor.pockafka.api;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public interface OutputStream {

    String LOGIN_SUCCESS = "poc-kafka-login-success-output";
    String LOGIN_FAILURE = "poc-kafka-login-failure-output";
    String LOGOUT = "poc-kafka-logout-output";

    @Output(OutputStream.LOGIN_SUCCESS)
    MessageChannel loginSuccessOutput();

    @Output(OutputStream.LOGIN_FAILURE)
    MessageChannel loginFailureOutput();

    @Output(OutputStream.LOGOUT)
    MessageChannel logoutOutput();
}
