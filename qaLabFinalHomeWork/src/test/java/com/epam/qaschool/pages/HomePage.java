package com.epam.qaschool.pages;

import org.openqa.selenium.WebDriver;
import org.slf4j.LoggerFactory;

/**
 * Home page
 */
public class HomePage extends Page {
	
	private final static org.slf4j.Logger log = LoggerFactory.getLogger(HomePage.class);

	public HomePage(WebDriver webDriver) {
		super(webDriver);
		log.debug("HomePage was created!");
	}
	
	public void openPage(String URL){
		driver.get(URL);
	}
	
	public String getPageTitle(){
		String title = driver.getTitle();
		return title;
	}
	
	public boolean verifyBasePageTitle() {
		String expectedPageTitle = "Ebay";
		return getPageTitle().contains(expectedPageTitle);
	}
}
