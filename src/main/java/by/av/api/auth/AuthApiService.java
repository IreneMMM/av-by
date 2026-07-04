package by.av.api.auth;

import by.av.api.BaseApiService;
import by.av.domain.User;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthApiService extends BaseApiService {
    private static final Logger log = LogManager.getLogger(AuthApiService.class);
    private static final String SIGN_IN_URL = BASE_URL + "/auth/login/sign-in";

    @Step("POST /auth/login/sign-in with login: {login}")
    public void login(String login, String password) {
        log.info("Sending POST request to {}", SIGN_IN_URL);
        log.debug("Request body: login={}, password={}", login, password);

        User body = new User(login, password);
        response = given().contentType(ContentType.JSON).body(body).when().post(SIGN_IN_URL);

        logResponse(log);
    }

    @Step("POST /auth/login/sign-in without body")
    public void loginWithoutBody() {
        log.info("Sending POST request without body to {}", SIGN_IN_URL);

        response = given().contentType(ContentType.JSON).when().post(SIGN_IN_URL);

        logResponse(log);
    }

    @Step("POST /auth/login/sign-in with login only: {login}")
    public void loginWithLoginOnly(String login) {
        log.info("Sending POST request with login only to {}", SIGN_IN_URL);
        log.debug("Request body: login={}", login);

        response = given().contentType(ContentType.JSON).body(Map.of("login", login)).when().post(SIGN_IN_URL);

        logResponse(log);
    }

    @Step("POST /auth/login/sign-in with password only")
    public void loginWithPasswordOnly(String password) {
        log.info("Sending POST request with password only to {}", SIGN_IN_URL);
        log.debug("Request body: password={}", password);

        response = given().contentType(ContentType.JSON).body(Map.of("password", password)).when().post(SIGN_IN_URL);

        logResponse(log);
    }
}
