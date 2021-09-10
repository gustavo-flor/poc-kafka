package com.github.gustavoflor.pockafka.api;

import org.mockito.BDDMockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@EmbeddedKafka
public abstract class AbstractBaseTest {

    @MockBean
    OutputStream outputStream;

    @MockBean(name = OutputStream.LOGOUT)
    MessageChannel logoutOutput;

    @MockBean(name = OutputStream.LOGIN_SUCCESS)
    MessageChannel loginSuccessOutput;

    @MockBean(name = OutputStream.LOGIN_FAILURE)
    MessageChannel loginFailureOutput;

    public void stubBrokerOutputStreams() {
        BDDMockito.given(outputStream.loginSuccessOutput()).willReturn(loginSuccessOutput);
        BDDMockito.given(outputStream.loginFailureOutput()).willReturn(loginFailureOutput);
        BDDMockito.given(outputStream.logoutOutput()).willReturn(logoutOutput);
    }

}
