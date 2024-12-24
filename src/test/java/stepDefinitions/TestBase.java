package stepDefinitions;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import constants.Browser;
import pages.HomePage;
import utility.BrowserUtility;
import utility.LambdaTestUtility;
import utility.LoggerUtility;

public class TestBase {
	protected HomePage homePage;
	Logger logger = LoggerUtility.getLogger(this.getClass());
	private boolean isLambdaTest;

	public void setup(String browser, boolean isLambdaTest, boolean isHeadless) {
		logger.info("Initializing WebDriver for: " + browser);

		try {
			homePage = new HomePage(Browser.valueOf(browser.toUpperCase()), isHeadless);
			logger.info("HomePage initialized successfully.");
		} catch (Exception e) {
			logger.error("Failed to initialize HomePage: ", e);
			throw e;
		}
	}


//	public BrowserUtility getInstance() {
//		return homePage;
//	}


	public void tearDown() {

//		if (isLambdaTest) {
//			LambdaTestUtility.quitSession(); // quit or close the browsersession on LT
//		} else {
		if(homePage!=null){
			homePage.quit(); // local
		} else {
			System.out.println("HomePage object is null. Skipping quit.");
		}

//		}
	}
}

