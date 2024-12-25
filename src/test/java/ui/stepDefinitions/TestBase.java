package ui.stepDefinitions;

import ui.constants.Browser;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import ui.pages.HomePage;
import ui.utility.LambdaTestUtility;
import ui.utility.LoggerUtility;

public class TestBase {
	protected HomePage homePage;
	Logger logger = LoggerUtility.getLogger(this.getClass());
	private boolean isLambdaTest;

	public void setup(String browser, boolean isLambdaTest, boolean isHeadless, Scenario scenario) {
		this.isLambdaTest = isLambdaTest;

		if (isLambdaTest) {
			// Initialize LambdaTest session
			WebDriver lambdaDriver = LambdaTestUtility.initializeLambdaTestSession(browser, scenario.getName());
			homePage = new HomePage(lambdaDriver);
		} else {
			// Running the test on local machine
			logger.info("Initializing local browser session for: " + browser);
			homePage = new HomePage(Browser.valueOf(browser.toUpperCase()), isHeadless);
			logger.info("HomePage initialized successfully.");
		}
	}

	public void tearDown() {
		if (isLambdaTest) {
			LambdaTestUtility.quitSession(); // Quit the LambdaTest session
		} else if (homePage != null) {
			homePage.quit(); // Close the local browser session
		} else {
			logger.warn("HomePage object is null. Skipping browser quit.");
		}
	}
}
