package by.av.api;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AuthApiService {
    private Response response;

    private final String baseUrl = "https://web-api.av.by";

    public void login(String login, String password) {
        LoginRequest body = new LoginRequest(login, password);
        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(baseUrl + "/auth/login/sign-in");
    }

    public void loginWithoutBody() {
    response = given()
            .contentType(ContentType.JSON)
            .when()
            .post(baseUrl + "/auth/login/sign-in");
}

    public int getStatusCode() {
        return response.getStatusCode();
    }

    public String getResponseBodyValue(String path) {
        return response.jsonPath().getString(path);
    }
}