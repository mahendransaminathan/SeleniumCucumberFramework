package stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.HomePage;

import java.io.File;
import java.io.IOException;

public class Hooks extends TestBase {

    private static final Logger logger = LogManager.getLogger(Hooks.class);
    private HomePage homePage = new HomePage();

    @Before
    public void initializeTest(Scenario scenario) {
        logger.info("Initializing Test for Scenario: " + scenario.getName());
        setup("chrome", true, true); // Ensure this setup works with your WebDriver configuration
    }

    @After
    public void tearDownTest(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.error("Scenario failed: " + scenario.getName());
        } else {
            logger.info("Scenario passed: " + scenario.getName());
        }
        tearDown(); // Clean up after each test
        logger.info("Test teardown complete for Scenario: " + scenario.getName());
    }

    @AfterStep
    public void afterEachStep(Scenario scenario) throws IOException {
        // Log step completion
        logger.info("Completed Step: " + scenario.getName());

        // Capture screenshot after each step
        File screenshot = ((TakesScreenshot) homePage.getDriver()).getScreenshotAs(OutputType.FILE);
        byte[] fileContent = FileUtils.readFileToByteArray(screenshot);
        scenario.attach(fileContent, "image/png", "Screenshot");
    }
}
