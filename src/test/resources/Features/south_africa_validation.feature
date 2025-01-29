Feature: Validate South Africa's JSON Schema

  Scenario: Validate the schema for South Africa response
    Given I make a GET request to "https://restcountries.com/v3.1/name/south%20africa?fullText=true"
    Then the response should match the schema "src/test/resources/schemas/south_africa_schema.json"