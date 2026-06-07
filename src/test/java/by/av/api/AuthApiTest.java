package by.av.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthApiTest extends BaseApiTest {
    @DisplayName("Check sign-in returns 400 when credentials are invalid")
    @Test
    public void testSignInWithInvalidCredentials() {
        authApiService.login(randomEmail(), "test@10000-0");
        assertEquals(400, authApiService.getStatusCode());

        String expectedMessage = "exception.auth.invalid_sign_in";
        String expectedText = "Неверный логин или пароль. Если забыли пароль, восстановите его";
        assertResponseMessages(expectedMessage, expectedText);
    }

    @DisplayName("Check sign-in returns 400 when credentials are empty")
    @Test
    public void testSignInWithEmptyCredentials() {
        authApiService.login("", "");
        assertEquals(400, authApiService.getStatusCode());

        String expectedMessage = "exception.validation.failed";
        String expectedText = "Запрос не соответствует правилам валидации";
        assertResponseMessages(expectedMessage, expectedText);
    }

    @DisplayName("Check sign-in returns 400 when request body is missing")
    @Test
    public void testSignInWithoutRequestBody() {
        authApiService.loginWithoutBody();
        assertEquals(400, authApiService.getStatusCode());

        String expectedMessage = "exception.request.invalid";
        String expectedText = "Неверный запрос";
        assertResponseMessages(expectedMessage, expectedText);
    }

    @DisplayName("Check sign-in returns 400 when password is empty")
    @Test
    public void testSignInWithEmptyPassword() {
        authApiService.login("test@gmail.com", "");
        assertEquals(400, authApiService.getStatusCode());

        String expectedMessage = "exception.validation.failed";
        String expectedText = "Запрос не соответствует правилам валидации";
        String expectedLoginError = "Заполните оба поля";
        assertResponseMessages(expectedMessage, expectedText, expectedLoginError);
    }

    @DisplayName("Check sign-in returns 400 when login is empty")
    @Test
    public void testSignInWithEmptyLogin() {
        authApiService.login("", "test");
        assertEquals(400, authApiService.getStatusCode());

        String expectedMessage = "exception.validation.failed";
        String expectedText = "Запрос не соответствует правилам валидации";
        String expectedLoginError = "Заполните оба поля";
        assertResponseMessages(expectedMessage, expectedText, expectedLoginError);
    }
}
