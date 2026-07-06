package by.av.api.data;

import by.av.ui.data.TestData;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public final class AuthApiDataProvider {
    private static final TestData testData = new TestData();
    private static final String RANDOM_LOGIN = testData.randomEmail();
    private static final String RANDOM_PASSWORD = testData.randomLengthPassword();
    private static final String EMPTY_VALUE = "";
    private static final int PASSWORD_MIN_BOUNDARY = 1;
    private static final int PASSWORD_MAX_BOUNDARY = 50;

    private AuthApiDataProvider() {
    }

    public static Stream<Arguments> provideEmptyCredentials() {
        return Stream.of(
                Arguments.of(EMPTY_VALUE, RANDOM_PASSWORD),
                Arguments.of(RANDOM_LOGIN, EMPTY_VALUE),
                Arguments.of(EMPTY_VALUE, EMPTY_VALUE)
        );
    }

    public static Stream<Integer> providePasswordBoundaryLengths() {
        return Stream.of(PASSWORD_MIN_BOUNDARY, PASSWORD_MAX_BOUNDARY);
    }
}
