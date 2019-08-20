package in.amazon.tests;

import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import commonLibs.implementations.CommonDriver;
import commonLibs.implementations.ScreenshotControl;
import commonLibs.utils.ConfigFileReader;
import in.amazon.pages.Homepage;

public class BaseTest {

	CommonDriver cmnDriver;
	Homepage homepage;

	ScreenshotControl screenshotControl;

	ExtentHtmlReporter htmlReporter;
	ExtentReports extentReport;
	ExtentTest extentTest;

	String executionStartTime;
	String currentWorkingDirectory;
	String reportFilename;

	Properties configProperty;
	String configFileName;

	String browserType;
	String baseUrl;
	WebDriver driver;

	@BeforeSuite
	public void beforeSuite() throws Exception {

		initializeExecutionStartTime();

		initializeCurrentWorkingDirectory();

		initializeConfigProperty();

		initializeExtentReport();
	}

	private void initializeConfigProperty() throws Exception {

		configFileName = String.format("%s/config/config.properties", currentWorkingDirectory);
		configProperty = ConfigFileReader.readConfigProperties(configFileName);

	}

	@BeforeClass
	public void setup() throws Exception {

		extentTest = extentReport.createTest("TC - Setting up the tests");

		invokeBrowser();

		initializeScreenshotVariable();

	}

	@AfterClass
	public void cleanUp() throws Exception {
		extentTest = extentReport.createTest("TC - Cleaning up the tests");

		extentTest.log(Status.INFO, "Closing the browser");
		cmnDriver.closeAllBrowsers();
	}

	@AfterMethod
	public void afterATestcase(ITestResult result) throws Exception {

		String testcaseName = result.getName();

		String filename = String.format("%s/screenshots/%s_%s.jpeg", currentWorkingDirectory, testcaseName,
				executionStartTime);

		if (result.getStatus() == ITestResult.FAILURE) {
			screenshotControl.captureAndSaveScreenshot(filename);

			extentTest.log(Status.FAIL, "Failed testcase : " + testcaseName);

			extentTest.addScreenCaptureFromPath(filename);
		} else if (result.getStatus() == ITestResult.SKIP) {

			extentTest.log(Status.SKIP, "Skipped testcase : " + testcaseName);

		}
	}

	@AfterSuite
	public void postCleanup() {
		extentReport.flush();
	}

	private void initializeExtentReport() {
		reportFilename = String.format("%s/reports/AmazonTestReport_%s.html", currentWorkingDirectory,
				executionStartTime);
		htmlReporter = new ExtentHtmlReporter(reportFilename);

		extentReport = new ExtentReports();
		extentReport.attachReporter(htmlReporter);

	}

	private void initializeCurrentWorkingDirectory() {
		currentWorkingDirectory = System.getProperty("user.dir");

	}

	private void initializeExecutionStartTime() {
		Date date = new Date();
		executionStartTime = String.valueOf(date.getTime());

	}

	private void initializeScreenshotVariable() {
		screenshotControl = new ScreenshotControl(driver);

	}

	private void invokeBrowser() throws Exception {
		browserType = configProperty.getProperty("browserType");

		extentTest.log(Status.INFO, "Initializing Browser : " + browserType);

		cmnDriver = new CommonDriver("chrome");

		int pageloadtimeout = Integer.parseInt(configProperty.getProperty("pageloadtimeout"));

		extentTest.log(Status.INFO, "Pageload Timeout : " + pageloadtimeout);
		cmnDriver.setPageloadTimeout(pageloadtimeout);

		int implicitWait = Integer.parseInt(configProperty.getProperty("elementDetectionTimeout"));

		extentTest.log(Status.INFO, "Pageload Timeout : " + implicitWait);
		cmnDriver.setElementDetectionTimeout(implicitWait);

		baseUrl = configProperty.getProperty("baseUrl");
		extentTest.log(Status.INFO, "URL : " + baseUrl);
		cmnDriver.navigateToFirstUrl(baseUrl);

		driver = cmnDriver.getDriver();

	}

}
