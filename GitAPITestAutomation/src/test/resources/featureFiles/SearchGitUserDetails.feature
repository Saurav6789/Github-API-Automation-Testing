Feature: Search GitUser Details

  Background: 
    Given I send GET request on service URL "/users/saurav6789"
    When I retrieve the results

  Scenario: Fetch Details for a specific user
    Then the status code should be 200
    And I validate the values from response as
      | login      | Saurav6789|
      | name         |  Saurav Anand |
      | location  |Amsterdam |
      | bio           |QA Automation |
      | public_repos | 35 |
      | following |  39 |

  Scenario: Fetch Header Details from the specific user response
     Then I can get the header details 
     And I validate the header values from response as 
     |server|GitHub.com|
     |content-security-policy|default-src 'none'|
     |status|200 OK|
     |content-type|application/json; charset=utf-8|
     |X-Ratelimit-Limit|60|
     
