package com.servicenow.githubapitest.stepdefinitions;

import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicenow.githubapitest.client.ApiExecutor;
import com.servicenow.githubapitest.data.OrgDetails;
import com.servicenow.githubapitest.utilities.Log;
import com.servicenow.githubapitest.utilities.TestUtil;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SearchOrgDetails {

	BeforeActions beforeActions = new BeforeActions();
	public CloseableHttpResponse closeableHttpResponse;
	ApiExecutor apiExecutor = new ApiExecutor();
	public JSONObject responsejson = new JSONObject();
	public String responseString;
	public String strUrl;
	TestUtil testUtil = new TestUtil();
	Log log = new Log();

	@Given("^I send GET request for organization on service URL \"([^\"]*)\"$")
	public void i_send_GET_request_for_organization_on_service_URL(String serviceUrl) throws Throwable {
		log.startLog();
		String strBaseUrl = beforeActions.setUp();
		String strUrl = strBaseUrl + serviceUrl;
		log.info("Final URL: " + strUrl);
		closeableHttpResponse = apiExecutor.get(strUrl);

	}

	@When("^I retrieve the results for organization details$")
	public void i_retrieve_the_results_for_organization_details() throws Throwable {
		// JSON String
		responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		responsejson = new JSONObject(responseString);
		log.info("Reponse JSON is: " + responsejson);

	}

	@Then("^the status code of the response is (\\d+)$")
	public void the_status_code_of_the_response_is(int expectedStatusCode) throws Throwable {
		int actualStatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(actualStatusCode, expectedStatusCode, "Assert code is not 200");

	}

	@Then("^I validate that the values of login,name,company and location are$")
	public void i_validate_that_the_values_of_login_name_company_and_location_are(DataTable table) throws Throwable {
		ObjectMapper mapper= new ObjectMapper();
		//JSON to Java (De-Serialization)
		OrgDetails orgDetailsResObj= mapper.readValue(responseString, OrgDetails.class); //actual users object
	    log.info("The reponse in Java Object form:" +orgDetailsResObj );
	    List<List<String>> data = table.raw();
	    Assert.assertEquals(orgDetailsResObj.getLogin(), data.get(0).get(0));
	    Assert.assertEquals(orgDetailsResObj.getName(), data.get(0).get(1));
	    Assert.assertEquals(orgDetailsResObj.getCompany(), data.get(0).get(2));
	    Assert.assertEquals(orgDetailsResObj.getLocation(), data.get(0).get(3));
	
	}

}
