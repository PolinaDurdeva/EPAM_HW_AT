package com.epam.testschool.hw;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestEbayWithSeleniumWD {
	private static final String CHROME_DRIVER_PATH_PROPERTY = "/home/polina/Programs/chromedriver";
	private static final String URL = "http://ebay.com";
	private static final String XPATH_SEARCH = "//*[@id=\"gh-ac\"]";
	private static final String XPATH_BTN_SEARCH = "//*[@id=\"gh-btn\"]";
	private static final String XPATH_ELEMENT = "//a[@class = \"vip\"]";
	private static final String XPATH_NEXT_PAGE = "//a[@class = \"gspr next\"]";
	private static final String XPATH_ITEM_TITLE = "//h1[@id = \"itemTitle\"]";
	private static final String UNNECESSARY_TITLE_SUBSTRING  = "нажмите на эту ссылку, чтобы перейти к ";
	private static final int LIMIT_PAGES = 2;
	private WebDriver driver;
	
	@Test(dataProvider = "queriesForCheckTyposCorrection")
	public void checkTyposCorrection(String wrongQuery, String rightQuery) {
		driver.get(URL);
		try {
			WebElement searchLine = driver.findElement(By.xpath(XPATH_SEARCH));			
			searchLine.clear();
			searchLine.sendKeys(wrongQuery);
		} catch (NoSuchElementException noSearchBar) {
			Assert.fail("Search bar is not found on page", noSearchBar);
		}
		try {
			WebElement btn = driver.findElement(By.xpath(XPATH_BTN_SEARCH));			
			btn.click();
		} catch (NoSuchElementException noButton) {
			Assert.fail("Button \"search\" is not found on page", noButton);			
		}
		List<WebElement> links = null;
		int countPages = 0;
		while(countPages < LIMIT_PAGES){
			try {
				links = driver.findElements(By.xpath(XPATH_ELEMENT));		
			} catch (WebDriverException noGoods) {
				Assert.fail("No " + XPATH_ELEMENT + "element on page!", noGoods);
			}
			for (WebElement a: links){
				String title = a.getAttribute("title").toLowerCase();			
				if (!title.contains(rightQuery)){
					System.out.println(title);
					assertTrue(false, "Query "+ wrongQuery 
							+ " was not corrected or title doesn't contain query "+ rightQuery + " in title: "
							+ title);				
				}
			}
			try {
				WebElement nextPageLink = driver.findElement(By.xpath(XPATH_NEXT_PAGE));
				String nextPageHref = nextPageLink.getAttribute("href");
				driver.get(nextPageHref);
				countPages++;
			} catch (WebDriverException noNextPage) {
				break;
			}
		}
		assertTrue(true);
	}
	@Test(dataProvider = "queriesForCheckSnippet")
	public void checkSnippet(String query){
		driver.get(URL);
		try {
			WebElement searchLine = driver.findElement(By.xpath(XPATH_SEARCH));			
			searchLine.clear();
			searchLine.sendKeys(query);
		} catch (NoSuchElementException noSearchBar) {
			Assert.fail("Search bar is not found on page", noSearchBar);
		}
		try {
			WebElement btn = driver.findElement(By.xpath(XPATH_BTN_SEARCH));			
			btn.click();
		} catch (NoSuchElementException noButton) {
			Assert.fail("Button \"search\" is not found on page", noButton);			
		}
		List<WebElement> linkItems = null;
		ArrayList<String> hrefs = new ArrayList<String>();
		ArrayList<String> snippetTitles = new ArrayList<String>();
		String nextPageHref  = "";
		int countPages = 0;
		boolean isNextPageAvaliable = true;
		while(countPages < LIMIT_PAGES && isNextPageAvaliable){
			countPages++;
			try {
				linkItems = driver.findElements(By.xpath(XPATH_ELEMENT));		
			} catch (NoSuchElementException noGoods) {
				Assert.fail("No " + XPATH_ELEMENT + "element on page!", noGoods);
			}
			for (WebElement a: linkItems){
				snippetTitles.add(a.getAttribute("title").toLowerCase().replace(UNNECESSARY_TITLE_SUBSTRING, ""));
				hrefs.add(a.getAttribute("href"));
			}
			try {
				WebElement nextPageLink = driver.findElement(By.xpath(XPATH_NEXT_PAGE));
				nextPageHref = nextPageLink.getAttribute("href");				
			} catch (NoSuchElementException noNextpage) {
				isNextPageAvaliable = false;
			}
			for (int i = 0; i < hrefs.size(); i++) {
				String href = hrefs.get(i);
				String snippet = snippetTitles.get(i);
				driver.get(href);
				WebElement itemTitle = driver.findElement(By.xpath(XPATH_ITEM_TITLE));
				String title = itemTitle.getText().toLowerCase();
				System.out.println(title);
				if (!title.contains(snippet)){
					System.out.println(title);
					assertTrue(false);	
				}				
			}
			if (isNextPageAvaliable){
				driver.get(nextPageHref);				
			}
		}
		assertTrue(true);
	}
	@DataProvider
	public Object[][] queriesForCheckTyposCorrection() {
		return new Object[][] { 
						{"подушrа", "подушка"},
						{"бейзболка", "бейсболка"}, 
						{"кошилек", "кошелек"} 
		};
	}
	
	@DataProvider
	public Object[][] queriesForCheckSnippet() {
		return new Object[][] {
						{"автограф"}, 
						{"палатка"} 
		};
	}

	@BeforeClass
	public void setUpChromeDriver() {
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH_PROPERTY);
		driver = new ChromeDriver();
	}

	@AfterClass
	public void dismissChromeDriver() {
		driver.quit();
	}

}
