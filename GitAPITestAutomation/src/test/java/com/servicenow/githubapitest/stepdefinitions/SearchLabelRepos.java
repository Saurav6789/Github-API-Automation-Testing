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

public class SearchLabelRepos {
	
	BeforeActions beforeActions = new BeforeActions();
	public CloseableHttpResponse closeableHttpResponse;
	ApiExecutor apiExecutor = new ApiExecutor();
	public JSONObject responsejson = new JSONObject();
	public String responseString;
	public String strUrl;
	TestUtil testUtil = new TestUtil();
	Log log = new Log();
	
	@Given("^I send the GET request for  repo with label as bug on service URL \"([^\"]*)\"$")
	public void i_send_the_GET_request_for_repo_with_label_as_bug_on_service_URL(String serviceUrl) throws Throwable {
		
		log.startLog();
		String strBaseUrl = beforeActions.setUp();
		String strUrl = strBaseUrl + serviceUrl;
		log.info("Final URL: " + strUrl);
		closeableHttpResponse = apiExecutor.get(strUrl);
	     
	}

	@When("^I retrieve the results of the response with label as bug$")
	public void i_retrieve_the_results_of_the_response_with_label_as_bug() throws Throwable {
		responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		responsejson = new JSONObject(responseString);
		log.info("Reponse JSON is: " + responsejson);
	     
	}

	@Then("^I validate that status code of the required response is (\\d+)$")
	public void i_validate_that_status_code_of_the_required_response_is(int expectedStatusCode) throws Throwable {
		int actualStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(actualStatusCode, expectedStatusCode, "Assert code is not 200");
	     
	}

	@Then("^I validate that the values  of the fields id ,node_id,name,color,description, and score are$")
	public void i_validate_that_the_values_of_the_fields_id_node_id_name_color_description_and_score_are(Map<String, String> responseValues) throws Throwable {
		for (Map.Entry<String, String> responseValue : responseValues.entrySet()) {

			String path = responseValue.getKey();
			String expectedValue = responseValue.getValue();
			String realValue = testUtil.getValueByJPath(responsejson, "/items[0]/" + path);
			Assert.assertEquals(realValue, expectedValue);

		}

	}


	

}
