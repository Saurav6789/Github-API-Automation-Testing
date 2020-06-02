package com.servicenow.githubapitest.utilities;

import org.apache.log4j.Logger;

public class Log {

	private static Logger Log = Logger.getLogger("devpinoyLogger");

	// We can use it when starting tests
	public void startLog() {
		Log.info("Test is Starting...");
	}

	// Info Level Logs
	public void info(String message) {
		Log.info(message);
	}

}
