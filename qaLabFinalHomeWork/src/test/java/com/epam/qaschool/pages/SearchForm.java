package com.epam.qaschool.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchForm extends Page {
	private static final String SEARCH_BTN_XPATH       = ".//*[@id=\"gh-btn\"]";
    private static final String SEARCH_TEXT_AREA_XPATH = ".//*[@id=\"gh-ac\"]";
    
    @FindBy(xpath=SEARCH_BTN_XPATH)
    private WebElement searchButton;
    
    @FindBy(xpath=SEARCH_TEXT_AREA_XPATH)
    private WebElement searchBox;
    
	public SearchForm(WebDriver driver) {
		super(driver);
	}
	
	public ResultPage searchFor(String request){
		searchBox.clear();
		searchBox.sendKeys(request);
		searchButton.click();
		return new ResultPage(driver);
	}

}
