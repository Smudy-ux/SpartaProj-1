package com.sparta.academy.steps;

import com.sparta.academy.BaseTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.MatcherAssert;

import com.sparta.academy.models.LoginRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthSteps extends BaseTest {

    private String username;
    private String password;
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
}
