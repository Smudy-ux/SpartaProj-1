package com.sparta.academy;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.matchesRegex;
import static org.hamcrest.Matchers.not;

import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import io.restassured.response.Response;

public class AuthApiTest extends BaseTest {
    @Test
    public void loginSuccess() {
        Response response = given()
                .spec(requestSpec())
                .body(Map.of("username", USERNAME, "password", PASSWORD))
                .post(LOGIN_ENDPOINT)
                .then().statusCode(200)
                .extract().response();

        String token = response.jsonPath().getString("token");
        MatcherAssert.assertThat(token, not(emptyOrNullString()));
        MatcherAssert.assertThat(token, matchesRegex("^.+\\..+\\..+$"));
    }

    @Test
    public void loginFailedBadCredentials() {
        Response response = given()
                .spec(requestSpec())
                .body(Map.of("username", "some1", "password", "some2"))
                .post(LOGIN_ENDPOINT)
                .then().statusCode(401)
                .extract().response();

        MatcherAssert.assertThat(response.asString(), containsString("Unauthorized"));
    }
}
