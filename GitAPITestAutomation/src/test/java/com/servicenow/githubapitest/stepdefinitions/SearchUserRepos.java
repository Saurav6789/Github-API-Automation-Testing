package com.servicenow.githubapitest.stepdefinitions;


import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.servicenow.githubapitest.client.ApiExecutor;
import com.servicenow.githubapitest.utilities.Log;
import com.servicenow.githubapitest.utilities.TestUtil;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SearchUserRepos {

	BeforeActions beforeActions = new BeforeActions();
	Log log = new Log();
	public CloseableHttpResponse closeableHttpResponse;
	ApiExecutor apiExecutor = new ApiExecutor();
	public JSONObject responsejson = new JSONObject();
	public String responseString;
	public JSONArray responseArrayjson;
	TestUtil testUtil = new TestUtil();

	@Given("^I send the GET request for the User repos on service URL \"([^\"]*)\"$")

	public void i_send_the_GET_request_for_the_User_repos_on_service_URL(String serviceUrl) throws Throwable {
		log.startLog();
		String strBaseUrl = beforeActions.setUp();
		String strUrl = strBaseUrl + serviceUrl;
		log.info("Final URL: " + strUrl);
		closeableHttpResponse = apiExecutor.get(strUrl);

	}

	@When("^I retrive the response results for User repositories search$")
	public void i_retrive_the_response_results_for_User_repositories_search() throws Throwable {
		responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		log.info("The response:" +responseString);
	}

	@Then("^I validate the full name of the user repositories$")
	public void i_validate_the_full_name_of_the_user_repositories(DataTable table) throws Throwable {
		List<List<String>> data = table.raw();
		List<String> nameValues = testUtil.getValuesForGivenKey(responseString, "full_name");
		log.info("The name of five repositories " + nameValues);
		for (int i = 0; i < data.get(0).size(); i++) {
			Assert.assertEquals(nameValues.get(i), data.get(0).get(i));
		}

	}
	@Given("^I send the GET request for the searched User repos on service URL\"([^\"]*)\"$")
	public void i_send_the_GET_request_for_the_searched_User_repos_on_service_URL(String serviceUrl) throws Throwable {
		log.startLog();
		String strBaseUrl = beforeActions.setUp();
		String strUrl = strBaseUrl + serviceUrl;
		log.info("Final URL: " + strUrl);
		closeableHttpResponse = apiExecutor.get(strUrl);
		
	}

	@Then("^I validate the response code as (\\d+)$")
	public void i_validate_the_response_code_as(int expectedStatusCode) throws Throwable {
	 
		int actualStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(actualStatusCode, expectedStatusCode, "Assert code is not 200");
		
	}

	@Then("^I validate that the login name of the user are following$")
	public void i_validate_that_the_login_name_of_the_user_are_following(DataTable table) throws Throwable {
		
		
		List<List<String>> data = table.raw();
		List<String> nameValues = testUtil.extract(responseString, "login");
		log.info("The login names of the users " + nameValues);
		for (int i = 0; i < data.get(0).size(); i++) {
			Assert.assertEquals(nameValues.get(i), data.get(0).get(i));
		}
		
		
	}


		
}
