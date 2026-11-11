package com.sparta.academy.steps;

import io.restassured.response.Response;

/**
 * Shared per-scenario state. PicoContainer creates one instance per scenario
 * and injects it into every step class that asks for it in its constructor.
 */
public class TestContext {
    public Response response;
}
