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

  Scenario Outline: Get a specific spartan by id
    Given I am logged in
    When I request the spartan with id <id>
    Then the response status should be 200
    And the spartan should have id <id>, name "<firstName>" "<lastName>", university "<university>", course "<course>" and stream "<stream>"
    And the spartan should have self, update and delete links to itself
    And the spartan's course link should point to course <courseId>

    Examples:
      | id | firstName | lastName  | university                 | course   | stream   | courseId |
      | 1  | Sparty    | McFly     | University of Rome         | TECH 300 | C# Dev   | 1        |
      | 14 | Joan      | Clarke    | University of Camrbridge   | TECH 301 | Java Dev | 2        |
      | 33 | Noel      | Gallagher | University of Manchester   | DATA 304 | Data     | 5        |

  Scenario: Get a spartan that does not exist
    Given I am logged in
    When I request the spartan with id 999
    Then the response status should be 204
    And the response body should be empty

  Scenario: Get a spartan with an invalid id
    Given I am logged in
    When I request the spartan with id abc
    Then the response status should be 400
    And the response should contain "The value 'abc' is not valid."

  Scenario: Get a spartan without a token
    When I request the spartan with id 1 without a token
    Then the response status should be 401

  Scenario: Delete a spartan
    Given I am logged in
    And an existing spartan
    When I delete the spartan
    Then the response status should be 204
    And the response body should be empty
    And the spartan should no longer exist

  Scenario: Delete the same spartan twice
    Given I am logged in
    And an existing spartan
    And I delete the spartan
    When I delete the spartan
    Then the response status should be 404

  Scenario: Delete a spartan that does not exist
    Given I am logged in
    When I delete the spartan with id 999
    Then the response status should be 404
    And the response should contain "Not Found"

  Scenario: Delete a spartan with an invalid id
    Given I am logged in
    When I delete the spartan with id abc
    Then the response status should be 400
    And the response should contain "The value 'abc' is not valid."

  Scenario: Delete a spartan without a token
    Given an existing spartan
    When I delete the spartan without a token
    Then the response status should be 401
    And the spartan should still exist

  Scenario: Update a spartan's details
    Given I am logged in
    And an existing spartan
    When I update the spartan with first name "Updated", university "University of Updates", degree "Quality Assurance" and graduated true
    Then the response status should be 204
    And the response body should be empty
    And the spartan should now have first name "Updated", university "University of Updates", degree "Quality Assurance" and graduated true

  Scenario: Move a spartan to another course
    Given I am logged in
    And an existing spartan
    When I move the spartan to course "TECH 301"
    Then the response status should be 204
    And the spartan should now be on course "TECH 301" in stream "Java Dev" with a link to course 2

  Scenario: Updating a spartan with an invalid name is rejected
    Given I am logged in
    And an existing spartan
    When I update the spartan with first name "Tom"
    Then the response status should be 400
    And the response should contain "The field FirstName must be a string with a minimum length of 6"
    And the spartan should be unchanged

  Scenario: Updating a spartan when the body id does not match the URL id is rejected
    Given I am logged in
    And an existing spartan
    When I update the spartan with id 1 in the body
    Then the response status should be 400
    And the spartan should be unchanged

  Scenario: Updating a spartan without an id in the body is rejected
    Given I am logged in
    And an existing spartan
    When I update the spartan without an id in the body
    Then the response status should be 400
    And the spartan should be unchanged

  Scenario: Update a spartan that does not exist
    Given I am logged in
    When I update a spartan with id 999 that does not exist
    Then the response status should be 404
    And the response should contain "Not Found"

  Scenario: Update a spartan without a token
    Given an existing spartan
    When I update the spartan without a token
    Then the response status should be 401
    And the spartan should be unchanged
