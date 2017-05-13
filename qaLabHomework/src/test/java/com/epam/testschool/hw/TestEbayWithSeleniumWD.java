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
	private static final String XPATH_ELEMENT = "//*[@id=\"Results\"]//h3/a";
	private static final String XPATH_NEXT_PAGE = "//a[@class = \"gspr next\"]";
	private static final String XPATH_ITEM_TITLE = "//h1[@id = \"itemTitle\"]";
	private static final String NEW_ADVERTICEMENT_RU = "НОВОЕ ОБЪЯВЛЕНИЕ";
	private static final String NEW_ADVERTICEMENT_EN = "NEW ADVERTICEMENT";
	private static final String DOTS = "...";
	private static final String ATTRIBUTE_HREF = "href";
	private static final int LIMIT_PAGES = 1;
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
		boolean isNextPageAvaliable = true;
		String nextPageHref = "";
		while(countPages < LIMIT_PAGES && isNextPageAvaliable ){
			try {
				links = driver.findElements(By.xpath(XPATH_ELEMENT));		
			} catch (WebDriverException noGoods) {
				Assert.fail("No " + XPATH_ELEMENT + "element on page!", noGoods);
			}
			for (WebElement a: links){
				String title = a.getText()
						.replace(DOTS, "")
						.replace(NEW_ADVERTICEMENT_EN, "")
						.replace(NEW_ADVERTICEMENT_RU, "")
						.toLowerCase();
				assertTrue(title.contains(rightQuery), "Query "+ wrongQuery 
						+ " was not corrected or title doesn't contain query "+ rightQuery 
						+ " in title: "	+ title);
			}
			if (countPages != LIMIT_PAGES - 1) {
				try {
					WebElement nextPageLink = driver.findElement(By.xpath(XPATH_NEXT_PAGE));
					nextPageHref = nextPageLink.getAttribute(ATTRIBUTE_HREF);
					
				} catch (WebDriverException noNextPage) {
					isNextPageAvaliable = false;
				}
				if (isNextPageAvaliable){
					driver.get(nextPageHref);				
				}
			}
			countPages++;
		}
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
		String nextPageHref  = "";
		int countPages = 0;
		boolean isNextPageAvaliable = true;
		while(countPages < LIMIT_PAGES && isNextPageAvaliable){
			try {
				linkItems = driver.findElements(By.xpath(XPATH_ELEMENT));		
			} catch (NoSuchElementException noGoods) {
				Assert.fail("No " + XPATH_ELEMENT + "element on page!", noGoods);
			}
			ArrayList<String> hrefs = new ArrayList<String>();
			ArrayList<String> snippetTitles = new ArrayList<String>();
			for (WebElement a: linkItems){
				snippetTitles.add(
						a.getText()
						.replace(DOTS, "")
						.replace(NEW_ADVERTICEMENT_EN, "")
						.replace(NEW_ADVERTICEMENT_RU, "")
						.toLowerCase()
						);
				hrefs.add(a.getAttribute(ATTRIBUTE_HREF));
			}
			if (countPages != LIMIT_PAGES - 1) {				
				try {
					WebElement nextPageLink = driver.findElement(By.xpath(XPATH_NEXT_PAGE));
					nextPageHref = nextPageLink.getAttribute(ATTRIBUTE_HREF);				
				} catch (NoSuchElementException noNextpage) {
					isNextPageAvaliable = false;
				}
			} else {
				isNextPageAvaliable = false;
			}
			System.out.println(hrefs.size());
			for (int i = 0; i < hrefs.size(); i++) {
				System.out.println(i);
				String href = hrefs.get(i);
				String snippet = snippetTitles.get(i).trim();
				driver.get(href);
				WebElement itemTitle = driver.findElement(By.xpath(XPATH_ITEM_TITLE));
				String title = itemTitle.getText().toLowerCase();
				assertTrue(title.contains(snippet), "Title: "+ title + " doesn't contain:\n"+snippet);			
			}
			if (isNextPageAvaliable){
				driver.get(nextPageHref);				
			}
			countPages++;
		}
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
		//TODO: add this property in pom.xml
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH_PROPERTY);
		driver = new ChromeDriver();
	}

	@AfterClass
	public void dismissChromeDriver() {
		driver.quit();
	}

}
