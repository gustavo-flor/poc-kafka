package com.github.gustavoflor.pockafka.api;

import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@NoArgsConstructor
public class AuthServiceTest extends AbstractBaseTest {

    private AuthService authService;

    @Spy
    private Random random;

    @Captor
    private ArgumentCaptor<Message<?>> argumentCaptorKafkaSend;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        super.stubBrokerOutputStreams();
        authService = new AuthService(outputStream, random);
    }

    @Test
    public void shouldSendLoginSuccessMessage() {
        Mockito.when(random.nextBoolean()).thenReturn(true);
        final Credentials credentials = new Credentials("John Marston");
        authService.login(credentials);

        verify(loginSuccessOutput).send(argumentCaptorKafkaSend.capture());

        assertEquals(credentials, argumentCaptorKafkaSend.getValue().getPayload());
    }

    @Test
    public void shouldSendLoginFailureMessage() {
        Mockito.when(random.nextBoolean()).thenReturn(false);
        final Credentials credentials = new Credentials("John Marston");
        authService.login(credentials);

        verify(loginFailureOutput).send(argumentCaptorKafkaSend.capture());

        assertEquals(credentials, argumentCaptorKafkaSend.getValue().getPayload());
    }

    @Test
    public void shouldSendLogoutMessage() {
        final Credentials credentials = new Credentials();
        authService.logout(credentials);

        verify(logoutOutput).send(argumentCaptorKafkaSend.capture());

        assertEquals(credentials, argumentCaptorKafkaSend.getValue().getPayload());
    }


}
