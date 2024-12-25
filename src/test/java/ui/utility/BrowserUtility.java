package ui.utility;

import ui.constants.Browser;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class BrowserUtility {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
    Logger logger = LoggerUtility.getLogger(this.getClass());
    //private static final int eleVisTime = Integer.parseInt(PropertiesUtil.readProperty(Env.QA,"elementVisibilityTime"));
    WebDriverWait webDriverWait;

    public BrowserUtility() {

    }

    public WebDriver getDriver() {
        return driver.get();
    }

    public BrowserUtility(WebDriver driver) {
        super();
        this.driver.set(driver); // Initialize the instance variable driver
    }

    /*
     * public BrowserUtility(String browserName) {
     *
     * if(browserName.equalsIgnoreCase("chrome")) { driver = new ChromeDriver(); }
     * else if (browserName.equalsIgnoreCase("edge")) { driver = new EdgeDriver(); }
     * else {
     * System.err.println("Invalid Browser Name.. Please select Chrome or Edge"); }
     * }
     */
    public BrowserUtility(Browser browserName) {
        logger.info("Launching Browser for " + browserName);
        if (browserName == Browser.CHROME) {

            driver.set(new ChromeDriver());
        } else if (browserName == Browser.EDGE) {

            driver.set(new EdgeDriver());
        } else if (browserName == Browser.FIREFOX) {

            driver.set(new FirefoxDriver());
        } else {
            logger.error("Invalid Browser Name... Please select valid option");
            System.err.println("Invalid Browser Name... Please select valid option");
        }
    }

    public BrowserUtility(Browser browserName, boolean isHeadless) {
        logger.info("Launching Browser for " + browserName);
        if (browserName == Browser.CHROME) {
            if (isHeadless) {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless=old"); // headless mode launch of browser
                chromeOptions.addArguments("--window-size=1920,1800");
                driver.set(new ChromeDriver(chromeOptions));
            } else {
                driver.set(new ChromeDriver());

            }
        } else if (browserName == Browser.EDGE) {
            if (isHeadless) {
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--headless=old"); // headless mode launch of browser
                edgeOptions.addArguments("--window-size=1920,1800");
                driver.set(new EdgeDriver(edgeOptions));
            } else {
                driver.set(new EdgeDriver());

            }
        } else if (browserName == Browser.FIREFOX) {
            if (isHeadless) {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--headless=old"); // headless mode launch of browser
                firefoxOptions.addArguments("--window-size=1920,1800");
                driver.set(new FirefoxDriver(firefoxOptions));
            } else {
                driver.set(new FirefoxDriver());
            }

        }
    }

    //	public String getUrl(){
//
//		String url = PropertiesUtil.readProperty(Env.QA,"WebUrl");
//		System.out.println(url);
//		driver.get().get(url);
//		logger.info("User Navigated to the URL: " + url);
//
//		return url;
//	}
//	public String getUrl(String urlType){
//
//		String url="";
//		switch (urlType.toUpperCase())
//		{
//			case "WEBURL" : url = PropertiesUtil.readProperty(Env.QA,"WebUrl");
//				break;
//			default :
//		}
//		System.out.println(url);
//		driver.get().get(url);
//		logger.info("User Navigated to the URL: " + url);
//
//		return url;
//	}
    public void goToWebsite(String url) {
        logger.info("Visiting the website.." + url);
        driver.get().get(url);
    }

    public String getTitle() {
        String title = "";
        try {
            title = driver.get().getTitle();
        } catch (Exception e) {
            System.out.println("error occured inside getTitle  function" + e.getMessage());

        }
        return title;
    }

    public void assertEquals(String expectedval, String actualval, String Fieldname) {
        try {
            System.out.println("%^^" + expectedval);
            System.out.println("%^^" + actualval);
            Assert.assertEquals(actualval, expectedval);
            System.out.println("Text verification is successful on " + Fieldname + " field");
        } catch (Exception e) {
            System.out.println("Text verification failed on, " + Fieldname + " error description = " + e.getMessage());

        }
    }

    public boolean isElementDisplayed(By locator) {
        try {
            logger.info("Checking if element is present: " + locator);
            return !driver.get().findElements(locator).isEmpty();
        } catch (Exception e) {
            logger.error("Error while checking element presence: " + e.getMessage());
            return false;
        }
    }

    public void maximizeWindow() {
        logger.info("Maximizing the browser window");

        driver.get().manage().window().maximize();
    }

    public void clickOn(By locator) {
        logger.info("Finding element with the locator" + locator);

        WebElement element = driver.get().findElement(locator);
        logger.info("Element found and performing the click");

        element.click();
    }

    public void enterText(By locator, String textToEnter) {
        logger.info("Finding element with the locator" + locator);

        WebElement element = driver.get().findElement(locator);
        logger.info("Element found and now enter text" + textToEnter);

        element.sendKeys(textToEnter);

    }

    public void clearText(By textBoxLocator) {
        logger.info("Finding element with the locator" + textBoxLocator);

        WebElement element = driver.get().findElement(textBoxLocator);
        logger.info("Element found and clearing the text box field");

        element.clear();
    }

    public void selectFromDropDown(By dropDownLocator, String optionToSelect) {
        logger.info("Finding element with the locator" + dropDownLocator);
        WebElement element = driver.get().findElement(dropDownLocator);
        Select select = new Select(element);
        logger.info("Selecting the option " + optionToSelect);
        select.selectByVisibleText(optionToSelect);
    }

    public void enterSpecialKey(By locator, Keys keyToEnter) {
        logger.info("Finding element with the locator" + locator);

        WebElement element = driver.get().findElement(locator);
        logger.info("Element found and now enter the special key" + keyToEnter);

        element.sendKeys(keyToEnter);

    }

    public String getVisibleText(By locator) {

        logger.info("Finding element with the locator" + locator);

        WebElement element = driver.get().findElement(locator);

        logger.info("Element found and returning the visible test" + element.getText());

        return element.getText();
    }

    public String getVisibleText(WebElement element) {


        logger.info("Returning the visible test" + element.getText());

        return element.getText();
    }

    public List<String> getAllVisibleText(By locator) {

        logger.info("Finding element with the locator" + locator);

        List<WebElement> elementList = driver.get().findElements(locator);

        logger.info("Element found and now printing the list of Elements");
        List<String> visibleTextList = new ArrayList<>();
        for (WebElement element : elementList) {
            System.out.println(getVisibleText(element));
            visibleTextList.add(getVisibleText(element));
        }
        return visibleTextList;

    }

    //	public boolean isElementPresent(By locator) {
//		try {
//			logger.info("Checking if element is present: " + locator);
//			return !driver.get().findElements(locator).isEmpty();
//		} catch (Exception e) {
//			logger.error("Error while checking element presence: " + e.getMessage());
//			return false;
//		}
//	}
    public void waitVisibilityOfElement(By locator) {
        webDriverWait = new WebDriverWait(driver.get(), Duration.ofSeconds(StaticConstants.ELEMENT_VISIBILITY_TIME));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void addScreenshot(Scenario scenario) throws IOException {

        // Use the HomePage object to get the WebDriver instance
        TakesScreenshot screenshot = (TakesScreenshot) driver.get();
        File screenshotData = screenshot.getScreenshotAs(OutputType.FILE);
        // Convert the screenshot file to byte array
        byte[] fileContent = FileUtils.readFileToByteArray(screenshotData);

        // Attach the screenshot to the Cucumber scenario
        scenario.attach(fileContent, "image/png", "Step Screenshot");


//        if (scenario.isFailed()) {
//            // Use the HomePage object to get the WebDriver instance
//		TakesScreenshot screenshot = (TakesScreenshot) driver.get();
//		File screenshotData = screenshot.getScreenshotAs(OutputType.FILE);
//		// Convert the screenshot file to byte array
//		byte[] fileContent = FileUtils.readFileToByteArray(screenshotData);
//
//		// Attach the screenshot to the Cucumber scenario
//		scenario.attach(fileContent, "image/png", "screenshot");
    }

    public String takeScreenShot(String name) {
        TakesScreenshot screenshot = (TakesScreenshot) driver.get();

        File screenshotData = screenshot.getScreenshotAs(OutputType.FILE);

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH-mm-ss");
        String timeStamp = format.format(date);

        String path = "./screenshots/" + name + "  -  " + timeStamp + ".png";

        File screenshotFile = new File(path);
        try {
            FileUtils.copyFile(screenshotData, screenshotFile);
        } catch (IOException e) {

            e.printStackTrace();
        }
        return path;

    }

    public void quit() {

        driver.get().quit();
    }

}
