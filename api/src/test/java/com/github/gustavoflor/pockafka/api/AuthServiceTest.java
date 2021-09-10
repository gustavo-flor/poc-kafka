package com.github.gustavoflor.pockafka.api;

import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
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
    private ArgumentCaptor<Message<?>> argumentCaptorKfkaSend;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        super.stubBrokerOutputStreams();
        authService = new AuthService(outputStream, random);
    }

    @Test
    public void shouldSendLoginSuccessMessage() {
        Mockito.when(random.nextBoolean()).thenReturn(true);
        final String name = "John Marston";
        authService.login(new Credentials(name));

        verify(loginSuccessOutput).send(argumentCaptorKfkaSend.capture());

        assertEquals(String.format(OutputStream.LOGIN_SUCCESS_MESSAGE, name), argumentCaptorKfkaSend.getValue().getPayload());
    }

    @Test
    public void shouldSendLoginFailureMessage() {
        Mockito.when(random.nextBoolean()).thenReturn(false);
        final String name = "John Marston";
        authService.login(new Credentials(name));

        verify(loginFailureOutput).send(argumentCaptorKfkaSend.capture());

        assertEquals(String.format(OutputStream.LOGIN_FAILURE_MESSAGE, name), argumentCaptorKfkaSend.getValue().getPayload());
    }

    @Test
    public void shouldSendLogoutMessage() {
        authService.logout();

        verify(logoutOutput).send(argumentCaptorKfkaSend.capture());

        assertEquals(OutputStream.LOGOUT_MESSAGE, argumentCaptorKfkaSend.getValue().getPayload());
    }


}
