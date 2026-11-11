Feature: Courses

  Scenario: Get all courses
    When I request all courses
    Then the response status should be 200
    And the response should contain a list of courses

  Scenario Outline: Get a specific course by id
    When I request the course with id <id>
    Then the response status should be 200
    And the course should have id <id>, name "<name>", stream "<stream>" and trainer "<trainer>"
    And the course should contain spartan "<spartan>"
    And the course should have a valid self link

    Examples:
      | id | name     | stream   | trainer          | spartan        |
      | 1  | TECH 300 | C# Dev   | Phil Windridge   | Ada Lovelace   |
      | 2  | TECH 301 | Java Dev | Catherine French | Marie Curie    |
      | 5  | DATA 304 | Data     | Paula Savaglia   | Noel Gallagher |

  Scenario: Get a course that does not exist
    When I request the course with id 999
    Then the response status should be 204
    And the response body should be empty

  Scenario: Get a course with an invalid id
    When I request the course with id abc
    Then the response status should be 400
    And the response should contain "The value 'abc' is not valid."

  Scenario Outline: Unsupported methods on courses are rejected
    When I send a <method> request to the courses endpoint
    Then the response status should be 405
    And the "Allow" header should be "GET"

    Examples:
      | method |
      | POST   |
      | PUT    |
      | DELETE |
      | PATCH  |
