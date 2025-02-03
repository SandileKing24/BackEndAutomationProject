Feature: Validate languages of South Africa

  Scenario: Verify that South African Sign Language (SASL) is listed as an official language
    Given I retrieve the country data for South Africa from the country API
    When I check the list of official languages for South Africa
    Then South African Sign Language should be included in the official languages list
#    And I should log the languages in the test report
