package com.sparta.academy;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import com.sparta.academy.models.LoginRequest;

public class BaseTest {

    protected static final String BASE_URL = System.getProperty("baseUrl", "http://localhost:8080");

    protected static final String LOGIN_ENDPOINT = "/auth/login";
    protected static final String COURSES_ENDPOINT = "/api/courses";
    protected static final String SPARTANS_ENDPOINT = "/api/spartans";

    protected static final String USERNAME = "sparta";
    protected static final String PASSWORD = "global";

    private static String token;

    static {
        RestAssured.baseURI = BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    public static RequestSpecification authRequestSpec() {
        return new RequestSpecBuilder()
                .addRequestSpecification(requestSpec())
                .addHeader("Authorization", "Bearer " + getToken())
                .build();
    }

    public static String getToken() {
        if (token == null) {
            token = RestAssured
                    .given()
                    .spec(requestSpec())
                    .body(new LoginRequest(USERNAME, PASSWORD))
                    .when()
                    .post(LOGIN_ENDPOINT)
                    .then()
                    .statusCode(200)
                    .extract()
                    .path("token");
        }
        return token;
    }

    public static void clearToken() {
        token = null;
    }
}
