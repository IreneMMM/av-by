package by.av.api.data;

import by.av.ui.data.TestData;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public final class AuthApiDataProvider {
    private static final TestData testData = new TestData();
    private static final String RANDOM_LOGIN = testData.randomEmail();
    private static final String RANDOM_PASSWORD = testData.randomLengthPassword();
    private static final String EMPTY_VALUE = "";

    private AuthApiDataProvider() {
    }

    public static Stream<Arguments> provideEmptyCredentials() {
        return Stream.of(
                Arguments.of(EMPTY_VALUE, RANDOM_PASSWORD),
                Arguments.of(RANDOM_LOGIN, EMPTY_VALUE),
                Arguments.of(EMPTY_VALUE, EMPTY_VALUE)
        );
    }
}
