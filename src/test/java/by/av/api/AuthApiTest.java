package by.av.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static by.av.api.assertions.AuthApiAssertions.assertResponse;

public class AuthApiTest extends BaseApiTest {
    private static final int BAD_REQUEST = 400;
    private static final String INVALID_SIGN_IN_MESSAGE = "exception.auth.invalid_sign_in";
    private static final String INVALID_SIGN_IN_TEXT = "Неверный логин или пароль. Если забыли пароль, восстановите его";
    private static final String VALIDATION_FAILED_MESSAGE = "exception.validation.failed";
    private static final String VALIDATION_FAILED_TEXT = "Запрос не соответствует правилам валидации";
    private static final String REQUEST_INVALID_MESSAGE = "exception.request.invalid";
    private static final String REQUEST_INVALID_TEXT = "Неверный запрос";
    private static final String LOGIN_REQUIRED_ERROR = "Заполните оба поля";

    @DisplayName("Check sign-in returns 400 when credentials are invalid")
    @Test
    public void testSignInWithInvalidCredentials() {
        authApiService.login(testData.randomEmail(), testData.randomLengthPassword());
        assertResponse(authApiService, BAD_REQUEST, INVALID_SIGN_IN_MESSAGE, INVALID_SIGN_IN_TEXT);
    }

    @DisplayName("Check sign-in returns 400 when request body is missing")
    @Test
    public void testSignInWithoutRequestBody() {
        authApiService.loginWithoutBody();
        assertResponse(authApiService, BAD_REQUEST, REQUEST_INVALID_MESSAGE, REQUEST_INVALID_TEXT);
    }

    @DisplayName("Check sign-in returns 400 when credentials are empty")
    @ParameterizedTest(name = "login = {0}, password = {1}")
    @MethodSource("by.av.api.data.AuthApiDataProvider#provideEmptyCredentials")
    public void testSignInWithEmptyCredentials(String login, String password) {
        authApiService.login(login, password);
        assertResponse(authApiService, BAD_REQUEST, VALIDATION_FAILED_MESSAGE, VALIDATION_FAILED_TEXT, LOGIN_REQUIRED_ERROR);
    }

    @DisplayName("Check sign-in returns 400 when password key is missing in JSON")
    @Test
    public void testSignInWithMissingPasswordKey() {
        authApiService.loginWithLoginOnly(testData.randomEmail());
        assertResponse(authApiService, BAD_REQUEST, VALIDATION_FAILED_MESSAGE, VALIDATION_FAILED_TEXT, LOGIN_REQUIRED_ERROR);
    }

    @DisplayName("Check sign-in returns 400 when login key is missing in JSON")
    @Test
    public void testSignInWithMissingLoginKey() {
        authApiService.loginWithPasswordOnly(testData.randomLengthPassword());
        assertResponse(authApiService, BAD_REQUEST, VALIDATION_FAILED_MESSAGE, VALIDATION_FAILED_TEXT, LOGIN_REQUIRED_ERROR);
    }
}
