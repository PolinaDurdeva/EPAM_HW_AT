package com.epam.qaschool.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FilterPopup extends Page{
	private static final String BEST_PRICES_FILTER_XPATH = "";
	
	@FindBy(xpath=BEST_PRICES_FILTER_XPATH)
	private WebElement bestPriceInfoOnSerp;

	public FilterPopup(WebDriver driver) {
		super(driver);
	}

}
