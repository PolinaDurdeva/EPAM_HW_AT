package pages;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PageResult {
	private static final String XPATH_ELEMENT = "//*[@id=\"Result\"]//h3/a";
	private static final String XPATH_ITEM_TITLE = "//h1[@id = \"itemTitle\"]";
	private WebDriver driver;
	
	public PageResult(WebDriver driver){
		this.driver = driver;
	}
	public ArrayList<String> getTitles(){
		List<WebElement> itemsOnPageResult = driver.findElements(By.xpath(XPATH_ELEMENT));
		ArrayList <String> titles = new ArrayList<String>();
		for (int i = 0; i < titles.size(); i++) {
			String title = itemsOnPageResult.get(i).getText().toLowerCase();
			titles.add(title);
		}
		return titles;
	}
}
