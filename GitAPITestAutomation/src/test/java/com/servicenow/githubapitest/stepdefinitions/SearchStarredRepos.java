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

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SearchStarredRepos {

	BeforeActions beforeActions = new BeforeActions();
	public CloseableHttpResponse closeableHttpResponse;
	ApiExecutor apiExecutor = new ApiExecutor();
	public JSONObject responsejson = new JSONObject();
	public String responseString;
	public String strUrl;
	public JSONArray responseArrayjson;
	TestUtil testUtil = new TestUtil();
	Log log = new Log();

	@Given("^I send the GET request for starred repos on service URL \"([^\"]*)\"$")
	public void i_send_the_GET_request_for_starred_repos_on_service_URL(String serviceUrl) throws Throwable {

		log.startLog();
		String strBaseUrl = beforeActions.setUp();
		String strUrl = strBaseUrl + serviceUrl;
		log.info("Final URL: " + strUrl);
		closeableHttpResponse = apiExecutor.get(strUrl);

	}

	@When("^I retrieve the results of the most starred repos$")
	public void i_retrieve_the_results_of_the_most_starred_repos() throws Throwable {
		responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		log.info("The response:" + responseString);

	}

	@Then("^the status code for the starred repos URL will be (\\d+)$")
	public void the_status_code_for_the_starred_repos_URL_will_be(int expectedStatusCode) throws Throwable {

		int actualStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(actualStatusCode, expectedStatusCode, "Assert code is not 200");

	}

	@Then("^I validate that the starred repos are in descending order$")
	public void i_validate_that_the_starred_repos_are_in_descending_order() throws Throwable {

		List<String> countValues = testUtil.extract(responseString, "stargazers_count");
		log.info("The count of starred repos in descending order: " + countValues);
		boolean actualValue = testUtil.isSortedDesc(countValues);
		Assert.assertEquals(actualValue, true);

	}

}
