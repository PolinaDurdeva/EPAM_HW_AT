package com.epam.qaschool.base;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Capabilities;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import ru.stqa.selenium.factory.WebDriverPool;

import com.example.util.PropertyLoader;

/**
 * Base class for TestNG-based test classes
 */
public class TestNgTestBase {

	private static final String CHROME_DRIVER_PATH_PROPERTY = "/home/polina/Programs/chromedriver";
	protected static String gridHubUrl;
	protected static String baseUrl;
	protected static Capabilities capabilities;
	protected final static org.slf4j.Logger log = LoggerFactory.getLogger(TestNgTestBase.class);

	protected WebDriver driver; 

	@BeforeSuite
	public void initTestSuite() throws IOException {
		baseUrl = PropertyLoader.loadProperty("site.url");
		gridHubUrl = PropertyLoader.loadProperty("grid.url");
		if ("".equals(gridHubUrl)) {
			gridHubUrl = null;
		}
		capabilities = PropertyLoader.loadCapabilities();
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH_PROPERTY);
	}

	@BeforeMethod
	public void initWebDriver() {
		driver = WebDriverPool.DEFAULT.getDriver(gridHubUrl, capabilities);
	}

	@AfterSuite(alwaysRun = true)
	public void tearDown() {
		WebDriverPool.DEFAULT.dismissAll();
	}
}
