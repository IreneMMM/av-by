package by.av.api;

import by.av.api.auth.AuthApiService;
import by.av.ui.data.TestData;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseApiTest {
    protected AuthApiService authApiService;
    protected TestData testData;

    @BeforeEach
    public void setUp() {
        authApiService = new AuthApiService();
        testData = new TestData();
    }
}
