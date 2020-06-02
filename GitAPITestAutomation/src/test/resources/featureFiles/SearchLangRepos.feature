Feature: Search Repositories based on language search criteria

  Scenario: Search Repositories for Python programming languages
    Given I send the GET request for Python repos on service URL "/search/repositories?q=language:python"
    When I retrive the response results
    Then I validate that the 'total_count' value range between 4000000 and 6000000
    And I validate the 	details of the first repository from response
      | id        |                         83222441 |
      | node_id   | MDEwOlJlcG9zaXRvcnk4MzIyMjQ0MQ== |
      | name      | system-design-primer             |
      | full_name | donnemartin/system-design-primer |
      | private   | false                            |
   