package StepDef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.Assert;

import java.io.File;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class SouthAfricaStepDefinitions {

    private String endpoint;
    private Response response;


    @Given("the api endpoint is {string}")
    public void the_api_endpoint_is(String url) {
        this.endpoint = url;

    }

    @When("I make a GET request to {string}")
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


    @Given("I retrieve the list of all countries from the country API")
    public void retrieveCountries() {
        response = given()
                .when()
                .get("https://restcountries.com/v3.1/all")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    @When("I count the number of countries in the response")
    public void countCountries() {
        // This is done automatically with the assertion in the next step
    }

    @Then("the number of countries should be 195")
    public void verifyCountryCount() {
        int numberOfCountries = response.jsonPath().getList("$").size();
        Assert.assertEquals(195, numberOfCountries);
    }

    @Then("I should log the total number of countries in the test report")
    public void logCountryCount() {
        int numberOfCountries = response.jsonPath().getList("$").size();
        System.out.println("Total number of countries: " + numberOfCountries);
    }


    @Given("I retrieve the country data for South Africa from the country API")
    public void retrieveSouthAfricaData() {
        response = given()
                .when()
                .get("https://restcountries.com/v3.1/name/south africa")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    @When("I check the list of official languages for South Africa")
    public void checkSouthAfricaLanguages() {
        // No action needed here, we will verify in the next step
    }

   @Then("South African Sign Language should be included in the official languages list")
   public void verifySASLInLanguages() {
       // Extract the list of languages from the response
       List<String> languages = response.jsonPath().getList("languages");
       System.out.println(languages);

       // Check if South African Sign Language (SASL) is in the list of languages
       boolean hasSASL = languages.contains("SASL");

       Assert.assertTrue("South African Sign Language should be listed as an official language", hasSASL);
   }


    @Then("I should log the languages in the test report")
    public void logLanguages() {
        // Extract the list of languages and print
        String[] languages = new String[]{response.jsonPath().get("languages.keySet().toArray()").toString()};
        System.out.println("Official languages of South Africa: " + String.join(", ", languages));
    }
}

