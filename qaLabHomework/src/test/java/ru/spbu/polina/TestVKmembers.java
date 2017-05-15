package ru.spbu.polina;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestVKmembers {
	private static final String COMPANY = "Место работы:";
	private static final String CITY = "Город:";
	private static final String BIRTHDAY = "День рождения:";
	private static final String SEPARATOR = ";";
	private static final String SPECIFY = "уточнить";
	private static final String FIO_LABEL = "FIO";
	private static final String FRIENDS_LABEL = "Friends";
	private static final int SCROLL_ATTEMPTS = 52;
	private static final String CHROME_DRIVER_PATH_PROPERTY = "/home/polina/Programs/chromedriver";
	private static final String URL = "https://vk.com/search?c%5Bgroup%5D=107004809&c%5Bsection%5D=people";
	private static final String XPATH_ELEMENT = "//div[@class=\"labeled name\"]/a";
	private static final String ATTRIBUTE_HREF = "href";
	private static final String PAGE_NAME = "//h2[@class=\"page_name\"]";
	private static final String SHORT_INFO = "//div[@class=\"labeled\"]";
	private static final String SHORT_INFO_LABEL = "//div[@class=\"label fl_l\"]";
	private static final String FRIENDS = ".//div[@class=\"count\"]";
	
	private WebDriver driver;
	
	@Test
	public void getAllMembers() {
		driver.get(URL);
		try {
			Robot robot = new Robot();
			for (int i = 0; i < SCROLL_ATTEMPTS; i++) {
				robot.keyPress(KeyEvent.VK_PAGE_DOWN);
				Thread.currentThread().sleep(1000);
				robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			}
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List <WebElement> allMembers = driver.findElements(By.xpath(XPATH_ELEMENT));
		System.out.println(allMembers.size());
		assertTrue(allMembers.size()==935);
		ArrayList<String> hrefs = new ArrayList<String>(allMembers.size());
		for (WebElement member: allMembers){
			hrefs.add(member.getAttribute(ATTRIBUTE_HREF));
		}
		
		try(PrintWriter writer = new PrintWriter(new File("./vkMembers.csv"));) {
			for (int i = 0; i < hrefs.size(); i++) {
				System.out.println((float)i/hrefs.size()*100);
				driver.get(hrefs.get(i));
				HashMap<String, String> map = new HashMap<String, String>();
				WebElement pageName = driver.findElement(By.xpath(PAGE_NAME));
				List<WebElement> friends = driver.findElements(By.xpath(FRIENDS));
				List<WebElement> shortInfo = driver.findElements(By.xpath(SHORT_INFO));
				List<WebElement> infoLabels = driver.findElements(By.xpath(SHORT_INFO_LABEL));
				for (int j = 0; j < shortInfo.size(); j++) {
					map.put(infoLabels.get(j).getText(), shortInfo.get(j).getText());
				}
				map.put(FIO_LABEL, pageName.getText());
				if (friends.size() > 0) {
					map.put(FRIENDS_LABEL, friends.get(1).getText());				
				}
				StringBuilder builder = new StringBuilder();
				builder
				.append(map.getOrDefault(FIO_LABEL, SPECIFY)).append(SEPARATOR)
				.append(map.getOrDefault(BIRTHDAY, SPECIFY)).append(SEPARATOR)
				.append(map.getOrDefault(CITY, SPECIFY)).append(SEPARATOR)
				.append(map.getOrDefault(FRIENDS_LABEL, SPECIFY)).append(SEPARATOR)
				.append(map.getOrDefault(COMPANY, SPECIFY)).append("\n");
				writer.write(builder.toString());
				writer.flush();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	@BeforeClass
	public void setUpChromeDriver() {
		//TODO: add this property in pom.xml
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH_PROPERTY);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("user-data-dir=/home/polina/.config/google-chrome/");
		driver = new ChromeDriver(options);
	}

	@AfterClass
	public void dismissChromeDriver() {
		driver.quit();
	}
}
