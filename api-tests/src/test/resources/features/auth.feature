Feature: Authentication

  Scenario: Login with valid credentials
    Given I have valid credentials
    When I send a login request
    Then the response status should be 200
    And the response should contain a valid token

  Scenario: Login with invalid credentials
    Given I have username "some_user" and password "some_password"
    When I send a login request
    Then the response status should be 401
    And the response should contain "Unauthorized"
