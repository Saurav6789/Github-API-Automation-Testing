Feature: Search repositories of a User

  Scenario: Search Repos for the User Saurav6789
    Given I send the GET request for the User repos on service URL "/users/Saurav6789/repos"
    When I retrive the response results for User repositories search
    Then I validate the full name of the user repositories
      | Saurav6789/aibooks.github.io | Saurav6789/Algebra | Saurav6789/Amazon-Recommendation-Systems | Saurav6789/Applied_AI_Course_Notes | Saurav6789/Artificial-Intelligence |

  Scenario: Search Repos for the User with name Saurav with repositories more than 42 and followers greater than 100
    Given I send the GET request for the searched User repos on service URL"/search/users?q=saurav+repos:%3E42+followers:%3E100"
    When I retrive the response results for User repositories search
    Then I validate the response code as 200
    Then I validate that the login name of the user are following
    |sauravtom|sauravkaushik8|sanketsaurav|ravsau|
