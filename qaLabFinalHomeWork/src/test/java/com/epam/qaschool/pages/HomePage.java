package com.epam.qaschool.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * Home page
 */
public class HomePage extends Page {

	public HomePage(WebDriver webDriver) {
		super(webDriver);
		log.debug("HomePagge was created!");
	}
	
	public void openPage(String URL){
		driver.get(URL);
	}
	
	public String getPageTitle(){
		String title = driver.getTitle();
		return title;
	}
	
	public boolean verifyBasePageTitle() {
		String expectedPageTitle="Ebay";
		return getPageTitle().contains(expectedPageTitle);
	}
}
