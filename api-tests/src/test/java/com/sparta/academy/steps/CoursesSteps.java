package com.sparta.academy.steps;

import com.sparta.academy.BaseTest;
import com.sparta.academy.models.CoursesRequestItem;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.hamcrest.MatcherAssert;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CoursesSteps extends BaseTest {

    private final TestContext context;

    public CoursesSteps(TestContext context) {
        this.context = context;
    }

    @When("I request all courses")
    public void iRequestAllCourses() {
        context.response = given()
                .spec(requestSpec())
                .when()
                .get(COURSES_ENDPOINT);
    }

    @And("the response should contain a list of courses")
    public void theResponseShouldContainAListOfCourses() {
        List<CoursesRequestItem> courses = Arrays.asList(context.response.as(CoursesRequestItem[].class));

        MatcherAssert.assertThat(courses, is(not(empty())));

        MatcherAssert.assertThat(courses, everyItem(allOf(
                hasProperty("id", greaterThan(0)),
                hasProperty("name", not(emptyOrNullString())),
                hasProperty("stream", not(emptyOrNullString())),
                hasProperty("trainer", not(emptyOrNullString())),
                hasProperty("spartans", not(empty())),
                hasProperty("startDate", matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}")),
                hasProperty("links", hasItem(allOf(
                        hasProperty("href", startsWith("/api/Courses/")),
                        hasProperty("rel", equalTo("self")),
                        hasProperty("method", equalTo("GET"))))))));

        MatcherAssert.assertThat(courses, hasItem(allOf(
                hasProperty("id", equalTo(1)),
                hasProperty("name", equalTo("TECH 300")),
                hasProperty("stream", equalTo("C# Dev")),
                hasProperty("trainer", equalTo("Phil Windridge")),
                hasProperty("spartans", hasItem("Ada Lovelace")),
                hasProperty("startDate", equalTo("2023-03-01T00:00:00")))));
    }
}
