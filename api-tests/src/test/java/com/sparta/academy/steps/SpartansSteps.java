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
        SpartanItem created = findSpartanByLastName(newSpartan.getLastName());

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

    @When("I request the spartan with id {word}")
    public void iRequestTheSpartanWithId(String id) {
        context.response = given()
                .spec(authRequestSpec())
                .when()
                .get(SPARTANS_ENDPOINT + "/" + id);
    }

    @When("I request the spartan with id {word} without a token")
    public void iRequestTheSpartanWithIdWithoutAToken(String id) {
        context.response = given()
                .spec(requestSpec())
                .when()
                .get(SPARTANS_ENDPOINT + "/" + id);
    }

    @And("the spartan should have id {int}, name {string} {string}, university {string}, course {string} and stream {string}")
    public void theSpartanShouldHave(int id, String firstName, String lastName,
                                     String university, String course, String stream) {
        SpartanItem spartan = context.response.as(SpartanItem.class);

        MatcherAssert.assertThat(spartan, allOf(
                hasProperty("id", equalTo(id)),
                hasProperty("firstName", equalTo(firstName)),
                hasProperty("lastName", equalTo(lastName)),
                hasProperty("university", equalTo(university)),
                hasProperty("degree", not(emptyOrNullString())),
                hasProperty("course", equalTo(course)),
                hasProperty("stream", equalTo(stream))));
    }

    @And("the spartan should have self, update and delete links to itself")
    public void theSpartanShouldHaveLinksToItself() {
        SpartanItem spartan = context.response.as(SpartanItem.class);
        String selfHref = "/api/Spartans/" + spartan.getId();

        MatcherAssert.assertThat(spartan.getLinks(), allOf(
                hasItem(allOf(
                        hasProperty("rel", equalTo("self")),
                        hasProperty("method", equalTo("GET")),
                        hasProperty("href", equalTo(selfHref)))),
                hasItem(allOf(
                        hasProperty("rel", equalTo("update_spartan")),
                        hasProperty("method", equalTo("PUT")),
                        hasProperty("href", equalTo(selfHref)))),
                hasItem(allOf(
                        hasProperty("rel", equalTo("delete_spartan")),
                        hasProperty("method", equalTo("DELETE")),
                        hasProperty("href", equalTo(selfHref))))));
    }

    @And("the spartan's course link should point to course {int}")
    public void theSpartansCourseLinkShouldPointToCourse(int courseId) {
        SpartanItem spartan = context.response.as(SpartanItem.class);

        MatcherAssert.assertThat(spartan.getLinks(), hasItem(allOf(
                hasProperty("rel", equalTo("course")),
                hasProperty("method", equalTo("GET")),
                hasProperty("href", equalTo("/api/Courses/" + courseId)))));
    }

    @Given("an existing spartan")
    public void anExistingSpartan() {
        iHaveAValidNewSpartan();
        given()
                .spec(authRequestSpec())
                .body(newSpartan)
                .post(SPARTANS_ENDPOINT);

        SpartanItem created = findSpartanByLastName(newSpartan.getLastName());
        MatcherAssert.assertThat("test spartan should have been created", created, notNullValue());
        context.createdSpartanId = created.getId();
    }

    @When("I delete the spartan")
    public void iDeleteTheSpartan() {
        context.response = given()
                .spec(authRequestSpec())
                .when()
                .delete(SPARTANS_ENDPOINT + "/" + context.createdSpartanId);
    }

    @When("I delete the spartan without a token")
    public void iDeleteTheSpartanWithoutAToken() {
        context.response = given()
                .spec(requestSpec())
                .when()
                .delete(SPARTANS_ENDPOINT + "/" + context.createdSpartanId);
    }

    @When("I delete the spartan with id {word}")
    public void iDeleteTheSpartanWithId(String id) {
        context.response = given()
                .spec(authRequestSpec())
                .when()
                .delete(SPARTANS_ENDPOINT + "/" + id);
    }

    @And("the spartan should no longer exist")
    public void theSpartanShouldNoLongerExist() {
        // GET by id returns 204 for an unknown id (see BUG-001)
        given()
                .spec(authRequestSpec())
                .get(SPARTANS_ENDPOINT + "/" + context.createdSpartanId)
                .then()
                .statusCode(204);

        MatcherAssert.assertThat(findSpartanByLastName(newSpartan.getLastName()), nullValue());
    }

    @And("the spartan should still exist")
    public void theSpartanShouldStillExist() {
        MatcherAssert.assertThat(findSpartanByLastName(newSpartan.getLastName()), notNullValue());
    }

    private SpartanItem findSpartanByLastName(String lastName) {
        List<SpartanItem> all = Arrays.asList(given()
                .spec(authRequestSpec())
                .get(SPARTANS_ENDPOINT)
                .as(SpartanItem[].class));

        return all.stream()
                .filter(s -> lastName.equals(s.getLastName()))
                .findFirst()
                .orElse(null);
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
