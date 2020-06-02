package com.servicenow.githubapitest.stepdefinitions;

import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;

import com.servicenow.githubapitest.client.ApiExecutor;
import com.servicenow.githubapitest.utilities.Log;
import com.servicenow.githubapitest.utilities.TestUtil;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SearchLangRepos {

	BeforeActions beforeActions = new BeforeActions();
	public CloseableHttpResponse closeableHttpResponse;
	ApiExecutor apiExecutor = new ApiExecutor();
	public JSONObject responsejson = new JSONObject();
	public String responseString;
	public String strUrl;
	TestUtil testUtil = new TestUtil();
	Log log = new Log();

	@Given("^I send the GET request for Python repos on service URL \"([^\"]*)\"$")
	public void i_send_the_GET_request_for_Python_repos_on_service_URL(String serviceUrl) throws Throwable {
		log.startLog();
		String strBaseUrl = beforeActions.setUp();
		String strUrl = strBaseUrl + serviceUrl;
		log.info("Final URL: " + strUrl);
		closeableHttpResponse = apiExecutor.get(strUrl);

	}

	@When("^I retrive the response results$")
	public void i_retrive_the_response_results() throws Throwable {
		responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		responsejson = new JSONObject(responseString);
		log.info("Reponse JSON is: " + responsejson);

	}

	@Then("^I validate that the 'total_count' value range between (\\d+) and (\\d+)$")
	public void i_validate_that_the_total_count_value_range_between_and(int minValue, int maxValue) throws Throwable {
		String totalCount = testUtil.getValueByJPath(responsejson, "/total_count");
		log.info("Total Count:" + totalCount);
		Assert.assertTrue(minValue <= Integer.parseInt(totalCount) && Integer.parseInt(totalCount) <= maxValue,
				"not in range");

	}

	@Then("^I validate the 	details of the first repository from response$")
	public void i_validate_the_details_of_the_first_repository_from_response(Map<String, String> responseValues)
			throws Throwable {

		for (Map.Entry<String, String> responseValue : responseValues.entrySet()) {
			String path = responseValue.getKey();
			String expectedValue = responseValue.getValue();
			String realValue = testUtil.getValueByJPath(responsejson, "/items[0]/" + path);
			Assert.assertEquals(realValue, expectedValue);
           
		}

	}

}
