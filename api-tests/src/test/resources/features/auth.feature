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

  Scenario Outline: Login is rejected for wrong credentials
    Given I have username "<username>" and password "<password>"
    When I send a login request
    Then the response status should be 401
    And the response should contain "Unauthorized"

    Examples:
      | username | password |
      | sparta   | nope     |
      | nope     | global   |
      | SPARTA   | global   |
      |          |          |

  Scenario Outline: Login is rejected for an invalid request body
    When I send a login request with body '<body>'
    Then the response status should be 400
    And the response should contain "<error>"

    Examples:
      | body                    | error                                 |
      | {"username":"sparta"}   | The Password field is required.       |
      | {"password":"global"}   | The Username field is required.       |
      | {}                      | The Username field is required.       |
      |                         | A non-empty request body is required. |
      | {"username":            | The loginRequest field is required.   |

  Scenario: Wrong username and wrong password give the same error
    Given I have username "nope" and password "global"
    And I send a login request
    And I save the response
    And I have username "sparta" and password "nope"
    When I send a login request
    Then the response should match the saved response, ignoring "traceId"

  Scenario: Login only accepts POST
    When I send a GET request to the login endpoint
    Then the response status should be 405

  Scenario: A valid token gives access to a protected endpoint
    Given I am logged in
    When I request all spartans with my token
    Then the response status should be 200

  Scenario Outline: A protected endpoint rejects a missing or invalid token
    When I request all spartans with <token> token
    Then the response status should be 401

    Examples:
      | token      |
      | no         |
      | a fake     |
      | a tampered |
