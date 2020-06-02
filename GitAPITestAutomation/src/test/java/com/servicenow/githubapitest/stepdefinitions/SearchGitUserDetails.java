package com.servicenow.githubapitest.stepdefinitions;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
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

public class SearchGitUserDetails {

	BeforeActions beforeActions = new BeforeActions();
	public CloseableHttpResponse closeableHttpResponse;
	ApiExecutor apiExecutor = new ApiExecutor();
	public JSONObject responsejson = new JSONObject();
	public String responseString;
	public String strUrl;
	TestUtil testUtil = new TestUtil();
	Log log = new Log();

	@Given("^I send GET request on service URL \"([^\"]*)\"$")
	public void i_send_GET_request_on_service_URL(String serviceUrl) throws Throwable {
		log.startLog();
		String strBaseUrl = beforeActions.setUp();
		String strUrl = strBaseUrl + serviceUrl;
		log.info("Final URL: " + strUrl);
		closeableHttpResponse = apiExecutor.get(strUrl);
	}

	@When("^I retrieve the results$")
	public void i_retrieve_the_results() throws Throwable {
		// JSON String
		responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		responsejson = new JSONObject(responseString);
		log.info("Reponse JSON is: " + responsejson);

	}

	@Then("^the status code should be (\\d+)$")
	public void the_status_code_should_be(int expectedStatusCode) throws Throwable {

		int actualStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(actualStatusCode, expectedStatusCode, "Assert code is not 200");
	}

	@Then("^I validate the values from response as$")
	public void i_validate_the_values_from_response_as(Map<String, String> responseValues) throws Throwable {

		for (Map.Entry<String, String> responseValue : responseValues.entrySet()) {

			String path = responseValue.getKey();
			String expectedValue = responseValue.getValue();
			String realValue = testUtil.getValueByJPath(responsejson, "/" + path);
			Assert.assertEquals(realValue, expectedValue);

		}

	}

	@Then("^I can get the header details$")
	public void i_can_get_the_header_details() throws Throwable {

		// All headers
		Header[] headerArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for (Header header : headerArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		log.info("Headers Array: " + allHeaders);

	}

	@Then("^I validate the header values from response as$")
	public void i_validate_the_header_values_from_response_as(Map<String, String> responseValues) throws Throwable {

		for (Map.Entry<String, String> responseValue : responseValues.entrySet()) {

			String path = responseValue.getKey();
			String expectedValue = responseValue.getValue();
			log.info("Expected Value: " + expectedValue);
			String realValue = closeableHttpResponse.getFirstHeader(path).getValue();
			log.info("Real Value: " + realValue);
			Assert.assertEquals(realValue, expectedValue);

		}

	}

}
