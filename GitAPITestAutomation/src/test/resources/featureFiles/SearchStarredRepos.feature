Feature: Search the Starred repos and check order

  Scenario: Validate the starred repos on Github and check their order
    Given I send the GET request for starred repos on service URL "/search/repositories?q=sort=stars&order=desc"
    When I retrieve the results of the most starred repos
    Then the status code for the starred repos URL will be 200
    And I validate that the starred repos are in descending order
