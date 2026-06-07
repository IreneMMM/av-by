package by.av.api;

import org.junit.jupiter.api.BeforeEach;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class BaseApiTest {
    protected AuthApiService authApiService;

    @BeforeEach
    public void setUp() {
        authApiService = new AuthApiService();
    }

    protected void assertResponseMessages(String expectedMessage, String expectedText) {
        assertAll(
                () -> assertEquals(expectedMessage, authApiService.getResponseBodyValue("message")),
                () -> assertEquals(expectedText, authApiService.getResponseBodyValue("messageText"))
        );
    }

    protected void assertResponseMessages(String expectedMessage, String expectedText, String expectedLoginError) {
        assertAll(
                () -> assertEquals(expectedMessage, authApiService.getResponseBodyValue("message")),
                () -> assertEquals(expectedText, authApiService.getResponseBodyValue("messageText")),
                () -> assertEquals(expectedLoginError, authApiService.getResponseBodyValue("context.errors.login[0]"))
        );
    }

    protected String randomEmail() {
        return "autotest_" + UUID.randomUUID().toString().substring(0, 8) + "@gmail.com";
    }
}
