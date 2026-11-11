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

    @When("I update the spartan with first name {string}, university {string}, degree {string} and graduated {word}")
    public void iUpdateTheSpartan(String firstName, String university, String degree, String graduated) {
        newSpartan.setId(context.createdSpartanId);
        newSpartan.setFirstName(firstName);
        newSpartan.setUniversity(university);
        newSpartan.setDegree(degree);
        newSpartan.setGraduated(Boolean.parseBoolean(graduated));
        sendPut(String.valueOf(context.createdSpartanId), true);
    }

    @When("I move the spartan to course {string}")
    public void iMoveTheSpartanToCourse(String course) {
        newSpartan.setId(context.createdSpartanId);
        newSpartan.setCourse(switch (course) {
            case "TECH 300" -> CourseRequest.tech300();
            case "TECH 301" -> CourseRequest.tech301();
            default -> throw new IllegalArgumentException("No test course for: " + course);
        });
        sendPut(String.valueOf(context.createdSpartanId), true);
    }

    @When("I update the spartan with first name {string}")
    public void iUpdateTheSpartanWithFirstName(String firstName) {
        newSpartan.setId(context.createdSpartanId);
        newSpartan.setFirstName(firstName);
        sendPut(String.valueOf(context.createdSpartanId), true);
    }

    @When("I update the spartan with id {int} in the body")
    public void iUpdateTheSpartanWithIdInTheBody(int bodyId) {
        newSpartan.setId(bodyId);
        newSpartan.setFirstName("Updated");
        sendPut(String.valueOf(context.createdSpartanId), true);
    }

    @When("I update the spartan without an id in the body")
    public void iUpdateTheSpartanWithoutAnIdInTheBody() {
        newSpartan.setId(null);
        newSpartan.setFirstName("Updated");
        sendPut(String.valueOf(context.createdSpartanId), true);
    }

    @When("I update a spartan with id {int} that does not exist")
    public void iUpdateASpartanThatDoesNotExist(int id) {
        iHaveAValidNewSpartan();
        newSpartan.setId(id);
        sendPut(String.valueOf(id), true);
    }

    @When("I update the spartan without a token")
    public void iUpdateTheSpartanWithoutAToken() {
        newSpartan.setId(context.createdSpartanId);
        newSpartan.setFirstName("Updated");
        sendPut(String.valueOf(context.createdSpartanId), false);
    }

    @And("the spartan should now have first name {string}, university {string}, degree {string} and graduated {word}")
    public void theSpartanShouldNowHave(String firstName, String university, String degree, String graduated) {
        MatcherAssert.assertThat(fetchCreatedSpartan(), allOf(
                hasProperty("id", equalTo(context.createdSpartanId)),
                hasProperty("firstName", equalTo(firstName)),
                hasProperty("lastName", equalTo(newSpartan.getLastName())),
                hasProperty("university", equalTo(university)),
                hasProperty("degree", equalTo(degree)),
                hasProperty("graduated", is(Boolean.parseBoolean(graduated)))));
    }

    @And("the spartan should now be on course {string} in stream {string} with a link to course {int}")
    public void theSpartanShouldNowBeOnCourse(String course, String stream, int courseId) {
        SpartanItem spartan = fetchCreatedSpartan();

        MatcherAssert.assertThat(spartan, allOf(
                hasProperty("course", equalTo(course)),
                hasProperty("stream", equalTo(stream))));
        MatcherAssert.assertThat(spartan.getLinks(), hasItem(allOf(
                hasProperty("rel", equalTo("course")),
                hasProperty("href", equalTo("/api/Courses/" + courseId)))));
    }

    @And("the spartan should be unchanged")
    public void theSpartanShouldBeUnchanged() {
        MatcherAssert.assertThat(fetchCreatedSpartan(), allOf(
                hasProperty("firstName", equalTo("Testing")),
                hasProperty("university", equalTo("University of Testing")),
                hasProperty("degree", equalTo("Software Testing"))));
    }

    private void sendPut(String urlId, boolean withToken) {
        context.response = given()
                .spec(withToken ? authRequestSpec() : requestSpec())
                .body(newSpartan)
                .when()
                .put(SPARTANS_ENDPOINT + "/" + urlId);
    }

    private SpartanItem fetchCreatedSpartan() {
        return given()
                .spec(authRequestSpec())
                .get(SPARTANS_ENDPOINT + "/" + context.createdSpartanId)
                .as(SpartanItem.class);
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
