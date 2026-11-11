package com.sparta.academy.steps;

import com.sparta.academy.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.MatcherAssert;

import com.sparta.academy.models.LoginRequest;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthSteps extends BaseTest {

    private String username;
    private String password;
    private String token;
    private final TestContext context;

    public AuthSteps(TestContext context) {
        this.context = context;
    }

    @Given("I have valid credentials")
    public void iHaveValidCredentials() {
        username = USERNAME;
        password = PASSWORD;
    }

    @Given("I have username {string} and password {string}")
    public void iHaveCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @When("I send a login request")
    public void iSendALoginRequest() {
        context.response = given()
                .spec(requestSpec())
                .body(new LoginRequest(username, password))
                .post(LOGIN_ENDPOINT);
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int status) {
        MatcherAssert.assertThat(context.response.statusCode(), is(status));
    }

    @Then("the response should contain a valid token")
    public void theResponseShouldContainAValidToken() {
        String token = context.response.jsonPath().getString("token");
        MatcherAssert.assertThat(token, not(emptyOrNullString()));
        MatcherAssert.assertThat(token, matchesRegex("^.+\\..+\\..+$"));
    }

    @Then("the response should contain {string}")
    public void theResponseShouldContain(String text) {
        MatcherAssert.assertThat(context.response.asString(), containsString(text));
    }

    @When("I send a login request with body {string}")
    public void iSendALoginRequestWithBody(String body) {
        context.response = given()
                .spec(requestSpec())
                .body(body)
                .post(LOGIN_ENDPOINT);
    }

    @When("I send a {word} request to the login endpoint")
    public void iSendARequestToTheLoginEndpoint(String method) {
        context.response = given()
                .spec(requestSpec())
                .request(method, LOGIN_ENDPOINT);
    }

    @And("I save the response")
    public void iSaveTheResponse() {
        context.previousResponse = context.response;
    }

    @Then("the response should match the saved response, ignoring {string}")
    public void theResponseShouldMatchTheSavedResponseIgnoring(String ignoredField) {
        Map<String, Object> saved = context.previousResponse.jsonPath().getMap("");
        Map<String, Object> current = context.response.jsonPath().getMap("");
        saved.remove(ignoredField);
        current.remove(ignoredField);

        MatcherAssert.assertThat(context.response.statusCode(), is(context.previousResponse.statusCode()));
        MatcherAssert.assertThat(current, equalTo(saved));
    }

    @Given("I am logged in")
    public void iAmLoggedIn() {
        token = getToken();
    }

    @When("I request all spartans with {word} token")
    public void iRequestAllSpartansWithToken(String tokenType) {
        RequestSpecification request = given().spec(requestSpec());

        switch (tokenType) {
            case "my" -> request.header("Authorization", "Bearer " + token);
            case "no" -> { }
            default -> throw new IllegalArgumentException("Unknown token type: " + tokenType);
        }

        context.response = request.get(SPARTANS_ENDPOINT);
    }

    @When("I request all spartans with a {word} token")
    public void iRequestAllSpartansWithAnInvalidToken(String tokenType) {
        String badToken = switch (tokenType) {
            case "fake" -> "abc.def.ghi";
            case "tampered" -> tamperedToken(getToken());
            default -> throw new IllegalArgumentException("Unknown token type: " + tokenType);
        };

        context.response = given()
                .spec(requestSpec())
                .header("Authorization", "Bearer " + badToken)
                .get(SPARTANS_ENDPOINT);
    }

    /** Changes the username claim inside a real token while keeping the original signature. */
    private static String tamperedToken(String validToken) {
        String[] parts = validToken.split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8)
                .replace("\"sparta\"", "\"admin\"");
        parts[1] = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        return String.join(".", parts);
    }
}
