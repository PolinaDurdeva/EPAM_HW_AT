package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {
	private static final String XPATH_SEARCH = "//*[@id=\"gh-ac\"]";
	private static final String XPATH_BTN_SEARCH = "//*[@id=\"gh-btn\"]";
	private WebDriver driver;
	
	public MainPage(WebDriver driver){
		this.driver = driver;
	}
	
	public void OpenPage(String URL) {
		this.driver.get(URL);
	}
	public void search(String request) {
		WebElement searchLine = driver.findElement(By.xpath(XPATH_SEARCH));			
		searchLine.clear();
		searchLine.sendKeys(request);
		WebElement btn = driver.findElement(By.xpath(XPATH_BTN_SEARCH));			
		btn.click();
	}
}
