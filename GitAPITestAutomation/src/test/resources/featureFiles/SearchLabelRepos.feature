Feature: Search the repository based on the labels

  Scenario: Search the labels present in the saurav6789 repos that matches label as bug
    Given I send the GET request for  repo with label as bug on service URL "/search/labels?repository_id=64778136&q=bug"
    When I retrieve the results of the response with label as bug
    Then I validate that status code of the required response is 200
    And I validate that the values  of the fields id ,node_id,name,color,description, and score are
      | name        | bug                     |
      | description | Something isn't working. |
      | score       |                     1.0 |
