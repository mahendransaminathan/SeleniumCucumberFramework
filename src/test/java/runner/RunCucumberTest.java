package runner;

import constants.Browser;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Optional;
import pages.HomePage;
import stepDefinitions.TestBase;
import utility.BrowserUtility;

import java.io.File;
import java.io.IOException;




@CucumberOptions(
        features = {"src/test/resources/features"}, // Path to your feature files
        glue = {"stepDefinitions"},      // Package containing step definitions
        plugin = {
                "pretty",                         // For readable console output
                "html:target/cucumber-reports/cucumber.html", // HTML report
                "json:target/cucumber-reports/cucumber.json",  // JSON report
                "rerun:target/rerun.txt", // To rerun failed tests
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        tags = "",                // Tags to filter scenarios
        monochrome = true,        // Better console output formatting
        publish = true
)
public class RunCucumberTest extends AbstractTestNGCucumberTests {


}
