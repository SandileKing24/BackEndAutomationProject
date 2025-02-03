Feature: Confirmation of the number of countries

  Scenario: Verify that there are 195 countries in the world
    Given I retrieve the list of all countries from the country API
    When I count the number of countries in the response
    Then the number of countries should be 195
    And I should log the total number of countries in the test report
