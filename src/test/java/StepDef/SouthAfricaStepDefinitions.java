package StepDef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.Assert;

import java.io.File;

import static io.restassured.RestAssured.given;

public class SouthAfricaStepDefinitions {
    private Response response;

    @Given("I make a GET request to {string}")
    public void i_make_a_get_request_to(String url) {
        RestAssured.baseURI = url;
        response = given()
                .when()
                .get()
                .then()
                .statusCode(200) // Check for status 200 OK
                .extract()
                .response();
    }

    @Then("the response should match the schema {string}")
    public void the_response_should_match_the_schema(String schemaPath) {
        try {
            // Validate the response body against the schema
            response.then().body(JsonSchemaValidator.matchesJsonSchema(new File(schemaPath)));
        } catch (Exception e) {
            Assert.fail("Schema validation failed: " + e.getMessage());
        }
    }
}

