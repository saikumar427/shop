Feature: Using for testing User Service Code

  Scenario: test get user
    Given user id as 2
    When get user from db with id 2
    Then user with name "kumar"
