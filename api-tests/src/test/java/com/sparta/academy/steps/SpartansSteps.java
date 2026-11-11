package com.sparta.academy.steps;

import com.sparta.academy.BaseTest;
import com.sparta.academy.models.CourseRequest;
import com.sparta.academy.models.SpartanItem;
import com.sparta.academy.models.SpartanRequest;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.MatcherAssert;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SpartansSteps extends BaseTest {

    private final TestContext context;
    private SpartanRequest newSpartan;

    public SpartansSteps(TestContext context) {
        this.context = context;
    }

    private List<SpartanItem> spartans() {
        return Arrays.asList(context.response.as(SpartanItem[].class));
    }

    @When("I request all spartans")
    public void iRequestAllSpartans() {
        context.response = given()
                .spec(authRequestSpec())
                .when()
                .get(SPARTANS_ENDPOINT);
    }

    @And("the response should contain a list of spartans")
    public void theResponseShouldContainAListOfSpartans() {
        List<SpartanItem> spartans = spartans();

        MatcherAssert.assertThat(spartans, is(not(empty())));

        MatcherAssert.assertThat(spartans, everyItem(allOf(
                hasProperty("id", greaterThan(0)),
                hasProperty("firstName", not(emptyOrNullString())),
                hasProperty("lastName", not(emptyOrNullString())),
                hasProperty("university", not(emptyOrNullString())),
                hasProperty("degree", not(emptyOrNullString())),
                hasProperty("course", not(emptyOrNullString())),
                hasProperty("stream", not(emptyOrNullString())),
                hasProperty("links", not(empty())))));
    }

    @And("every spartan should have self, update, delete and course links")
    public void everySpartanShouldHaveAllLinks() {
        MatcherAssert.assertThat(spartans(), everyItem(hasProperty("links", allOf(
                hasItem(allOf(
                        hasProperty("rel", equalTo("self")),
                        hasProperty("method", equalTo("GET")),
                        hasProperty("href", startsWith("/api/Spartans/")))),
                hasItem(allOf(
                        hasProperty("rel", equalTo("update_spartan")),
                        hasProperty("method", equalTo("PUT")),
                        hasProperty("href", startsWith("/api/Spartans/")))),
                hasItem(allOf(
                        hasProperty("rel", equalTo("delete_spartan")),
                        hasProperty("method", equalTo("DELETE")),
                        hasProperty("href", startsWith("/api/Spartans/")))),
                hasItem(allOf(
                        hasProperty("rel", equalTo("course")),
                        hasProperty("method", equalTo("GET")),
                        hasProperty("href", startsWith("/api/Courses/"))))))));
    }

    @Then("the list should contain spartan {string} {string} on course {string} in stream {string}")
    public void theListShouldContainSpartan(String firstName, String lastName, String course, String stream) {
        MatcherAssert.assertThat(spartans(), hasItem(allOf(
                hasProperty("firstName", equalTo(firstName)),
                hasProperty("lastName", equalTo(lastName)),
                hasProperty("course", equalTo(course)),
                hasProperty("stream", equalTo(stream)))));
    }

    @Given("I have a valid new spartan")
    public void iHaveAValidNewSpartan() {
        String lastName = "Tester" + System.currentTimeMillis();
        newSpartan = new SpartanRequest("Testing", lastName, "University of Testing",
                "Software Testing", CourseRequest.tech300(), false);
    }

    @Given("I have a new spartan with first name {string} and last name {string}")
    public void iHaveANewSpartanWithName(String firstName, String lastName) {
        newSpartan = new SpartanRequest(firstName, lastName, "University of Testing",
                "Software Testing", CourseRequest.tech300(), false);
    }

    @And("the spartan has no course")
    public void theSpartanHasNoCourse() {
        newSpartan.setCourse(null);
    }

    @When("I create the spartan")
    public void iCreateTheSpartan() {
        context.response = given()
                .spec(authRequestSpec())
                .body(newSpartan)
                .when()
                .post(SPARTANS_ENDPOINT);
    }

    @When("I create the spartan without a token")
    public void iCreateTheSpartanWithoutAToken() {
        context.response = given()
                .spec(requestSpec())
                .body(newSpartan)
                .when()
                .post(SPARTANS_ENDPOINT);
    }

    @Then("the spartan should be created with a generated id")
    public void theSpartanShouldBeCreatedWithAGeneratedId() {
        List<SpartanItem> all = Arrays.asList(given()
                .spec(authRequestSpec())
                .get(SPARTANS_ENDPOINT)
                .as(SpartanItem[].class));

        SpartanItem created = all.stream()
                .filter(s -> newSpartan.getLastName().equals(s.getLastName()))
                .findFirst()
                .orElse(null);

        MatcherAssert.assertThat("new spartan should be in the list", created, notNullValue());
        context.createdSpartanId = created.getId();

        SpartanItem fetched = given()
                .spec(authRequestSpec())
                .get(SPARTANS_ENDPOINT + "/" + created.getId())
                .as(SpartanItem.class);

        MatcherAssert.assertThat(fetched, allOf(
                hasProperty("id", greaterThan(0)),
                hasProperty("firstName", equalTo(newSpartan.getFirstName())),
                hasProperty("lastName", equalTo(newSpartan.getLastName())),
                hasProperty("university", equalTo(newSpartan.getUniversity())),
                hasProperty("degree", equalTo(newSpartan.getDegree())),
                hasProperty("course", equalTo("TECH 300")),
                hasProperty("stream", equalTo("C# Dev")),
                hasProperty("graduated", is(false))));
    }

    @After
    public void deleteCreatedSpartan() {
        if (context.createdSpartanId != null) {
            given()
                    .spec(authRequestSpec())
                    .delete(SPARTANS_ENDPOINT + "/" + context.createdSpartanId);
            context.createdSpartanId = null;
        }
    }
}
