package com.epam.qaschool.pages;

import org.openqa.selenium.WebDriver;

public class ProductPage extends Page{
	private static final String POSSIBILITY_OF_RETURN_XPATH = "";

	public ProductPage(WebDriver driver) {
		super(driver);
	}
	
	public boolean isReturnsCondition(){
		return false;
		
	}

}
