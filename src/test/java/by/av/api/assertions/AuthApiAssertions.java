package by.av.api.assertions;

import by.av.api.auth.AuthApiService;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

public final class AuthApiAssertions {
	private static final Logger log = LogManager.getLogger(AuthApiAssertions.class);
	private static final int TOO_MANY_REQUESTS = 429;
	private static final String RATE_LIMIT_MESSAGE = "API returned 429 Too Many Requests. Repeat this test later.";

	private AuthApiAssertions() {
    }

    @Step("Verify response: status={expectedStatusCode}, message={expectedMessage}")
    public static void assertResponse(
            AuthApiService authApiService,
            int expectedStatusCode,
            String expectedMessage,
            String expectedText
    ) {
		int actualStatusCode = authApiService.getStatusCode();
		skipTestIfRateLimitReached(actualStatusCode);

        assertAll(
                () -> assertEquals(expectedStatusCode, actualStatusCode),
                () -> assertEquals(expectedMessage, authApiService.getResponseBodyValue("message")),
                () -> assertEquals(expectedText, authApiService.getResponseBodyValue("messageText"))
        );
    }

    @Step("Verify response with login error: status={expectedStatusCode}, loginError={expectedLoginError}")
    public static void assertResponse(
            AuthApiService authApiService,
            int expectedStatusCode,
            String expectedMessage,
            String expectedText,
            String expectedLoginError
    ) {
		int actualStatusCode = authApiService.getStatusCode();
		skipTestIfRateLimitReached(actualStatusCode);

        assertAll(
                () -> assertEquals(expectedStatusCode, actualStatusCode),
                () -> assertEquals(expectedMessage, authApiService.getResponseBodyValue("message")),
                () -> assertEquals(expectedText, authApiService.getResponseBodyValue("messageText")),
                () -> assertEquals(expectedLoginError, authApiService.getResponseBodyValue("context.errors.login[0]"))
        );
    }

	private static void skipTestIfRateLimitReached(int actualStatusCode) {
		if (actualStatusCode == TOO_MANY_REQUESTS) {
			log.warn(RATE_LIMIT_MESSAGE);
		}
		assumeFalse(actualStatusCode == TOO_MANY_REQUESTS, RATE_LIMIT_MESSAGE);
	}
}
