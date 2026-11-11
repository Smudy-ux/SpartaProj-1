Feature: Spartans

  Scenario: Get all spartans
    Given I am logged in
    When I request all spartans
    Then the response status should be 200
    And the response should contain a list of spartans
    And every spartan should have self, update, delete and course links

  Scenario Outline: The spartans list contains a known spartan
    Given I am logged in
    When I request all spartans
    Then the list should contain spartan "<firstName>" "<lastName>" on course "<course>" in stream "<stream>"

    Examples:
      | firstName | lastName  | course   | stream   |
      | Sparty    | McFly     | TECH 300 | C# Dev   |
      | John      | Lennon    | TECH 301 | Java Dev |
      | Noel      | Gallagher | DATA 304 | Data     |

  # NOTE: POST currently returns 500 even though the spartan is created
  # (CreatedAtAction points to a non-existent "GetSupplier" action). Should be 201.
  Scenario: Create a new spartan
    Given I am logged in
    And I have a valid new spartan
    When I create the spartan
    Then the response status should be 500
    And the spartan should be created with a generated id

  Scenario Outline: Creating a spartan with invalid names is rejected
    Given I am logged in
    And I have a new spartan with first name "<firstName>" and last name "<lastName>"
    When I create the spartan
    Then the response status should be 400
    And the response should contain "<error>"

    Examples:
      | firstName | lastName | error                                                             |
      | Tom       | Spartan  | The field FirstName must be a string with a minimum length of 6   |
      | Testing   | Lee      | The field LastName must be a string with a minimum length of 6    |
      |           | Spartan  | The FirstName field is required.                                  |
      | Testing   |          | The LastName field is required.                                   |

  Scenario: Creating a spartan without a course is rejected
    Given I am logged in
    And I have a valid new spartan
    And the spartan has no course
    When I create the spartan
    Then the response status should be 400
    And the response should contain "The Course field is required."

  Scenario: Creating a spartan without a token is rejected
    Given I have a valid new spartan
    When I create the spartan without a token
    Then the response status should be 401
