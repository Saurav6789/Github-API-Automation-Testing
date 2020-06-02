package com.servicenow.githubapitest.stepdefinitions;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.servicenow.githubapitest.base.TestBase;

import cucumber.api.java.Before;

public class BeforeActions extends TestBase {

	@Before
	public String setUp() throws ClientProtocolException, IOException {

		String baseUrl = prop.getProperty("URL");
		return baseUrl;
	}

}
