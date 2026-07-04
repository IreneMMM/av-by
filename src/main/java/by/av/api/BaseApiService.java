package by.av.api;

import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;

public abstract class BaseApiService {
    protected static final String BASE_URL = "https://web-api.av.by";

    protected Response response;

    public int getStatusCode() {
        return response.getStatusCode();
    }

    public String getResponseBodyValue(String path) {
        return response.jsonPath().getString(path);
    }

    protected void logResponse(Logger log) {
        log.info("Response status: {}", response.getStatusCode());
        log.debug("Response body: {}", response.getBody().asString());
    }
}
