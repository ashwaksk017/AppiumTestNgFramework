package com.baseUtility;

import static com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromPath;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.codoid.products.exception.FilloException;
import com.utility.CommonUtility;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class BaseClassAndroid {
	public static AndroidDriver androidDriver;
	public AppiumDriverLocalService appService;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ITestResult result;
	public HashMap<String, String> data;
	public Logger log = LogManager.getLogger(this.getClass().getSimpleName());

	// public UiAutomator2Options options;
	/**
	 * Intialize all driver instances
	 * 
	 */
	@BeforeSuite
	public void startAppiumServer() {

		appService = new AppiumServiceBuilder()
				.withAppiumJS(
						new File("C://Users//ashwa//AppData//Roaming//npm//node_modules//appium//build//lib//main.js"))
				.withIPAddress("127.0.0.1").usingPort(4723).build();
		appService.start();
		
		try {
            //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String path = System.getProperty("user.dir") + "//reports//Automation//TestResult"
                    + now.toString().replaceAll(":", "-") + ".html";
            ExtentSparkReporter reporter = new ExtentSparkReporter(path);
            reporter.config().setReportName("Automation Results");
            reporter.config().setDocumentTitle("Test Results");

            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Tester", System.getProperty("user.name"));

            extent.setSystemInfo("Machine", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            log.error("Can not invoke extent report");
        }
    

	}

	@AfterMethod
	public void quitingDriver() {
		System.out.println("killing driver...");
		androidDriver.quit();
	}

	@AfterSuite // happens ONLY one time after finishing last test
	public void stopAppiumServer() {
		/*
		 * System.out.println("killing driver..."); driver.quit();
		 */
		System.out.println("stopAppiumServer..."); // add logs to see when it happens
		appService.stop();
	}

	@BeforeMethod // happens before EACH test start
	public void startAppiumDriver(ITestResult result) throws FilloException, IOException {
		data = new CommonUtility().getTestData(this.getClass().getSimpleName());
		System.out.println("startAppiumDriver"); // add logs to see when it happens
		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName("Android");
		String dirPath = CommonUtility.getDirePath();
		String appPath = dirPath + "//src//test//resources//General-Store.apk";
		options.setApp(appPath);
		try {
			androidDriver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	     test = extent.createTest(result.getMethod().getMethodName());
	}

	public Properties loadConfig() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "/src/main/resources/config.properties");
		prop.load(fis);
		return prop;
	}

	@AfterMethod(alwaysRun = true)
	public void getResult(ITestResult result) {
		try {
			String screenshotPath;
			if (result.getStatus() == ITestResult.FAILURE) {
				test.log(Status.FAIL,
						MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
				test.log(Status.FAIL,
						MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));

				screenshotPath = getScreenshotPath(result.getMethod().getMethodName(), androidDriver);
				test.fail(result.getName() + " - Test Case Failed",
						createScreenCaptureFromPath(screenshotPath).build());
			} else if (result.getStatus() == ITestResult.SKIP) {
				screenshotPath = getScreenshotPath(result.getMethod().getMethodName(), androidDriver);
				test.skip(result.getName() + " - Test Case Skipped",
						createScreenCaptureFromPath(screenshotPath).build());
			} else if (result.getStatus() == ITestResult.SUCCESS) {
				screenshotPath = getScreenshotPath(result.getMethod().getMethodName(), androidDriver);
				test.pass(result.getName() + " - Test Case Passed",
						createScreenCaptureFromPath(screenshotPath).build());
			}
			extent.flush();
		} catch (Exception e) {
			log.error("can not create extent report");
		}

	}

	public static String getScreenshotPath(String testCaseName, WebDriver driver) {
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			String destination = System.getProperty("user.dir") + "/reports/screenshot/" + testCaseName + "_"
					+ ThreadLocalRandom.current().nextInt() + ".png";
			FileUtils.copyFile(source, new File(destination));
			return destination;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

}
