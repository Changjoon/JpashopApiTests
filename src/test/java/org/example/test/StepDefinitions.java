package org.example.test;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;

public class StepDefinitions {

    private Response response;

    public StepDefinitions() {
    }

    @When("I send a POST request to the member API")
    public void i_send_a_post_request_to_the_member_api() {
        // API 호출
        String body = "{ \"name\": \"John\", \"city\": \"Seoul\", \"street\": \"Mapo\", \"zipcode\": \"12345\" }";

        response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(body)
                .post("http://localhost:8091/members/new");
    }
}