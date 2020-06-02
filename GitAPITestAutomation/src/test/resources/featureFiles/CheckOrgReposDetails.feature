Feature: Search the Details of the Organization

Scenario: Fetch the details of the organization

 Given I send GET request for organization on service URL "/users/octocat"
 When I retrieve the results for organization details 
 Then the status code of the response is 200
 And I validate that the values of login,name,company and location are 
 |octocat|The Octocat|GitHub|San Francisco|
 
   