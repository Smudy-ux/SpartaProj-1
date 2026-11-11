package com.sparta.academy.steps;

import io.restassured.response.Response;

public class TestContext {
    public Response response;
    public Response previousResponse;
    public Integer createdSpartanId;
}
