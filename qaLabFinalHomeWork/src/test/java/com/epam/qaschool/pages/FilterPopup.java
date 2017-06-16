package com.epam.qaschool.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FilterPopup extends Page{
	private static final String BEST_OFFER_FILTER_XPATH = ".//input[@id='LH_BO_1']";
	private static final String SUBMIT_BTN_XPATH = ".//form[@id='see-all-form']//input[@type='submit']";
	
	@FindBy(xpath=BEST_OFFER_FILTER_XPATH)
	private WebElement bestOfferCheckBox;
	
	@FindBy(xpath=SUBMIT_BTN_XPATH)
	private WebElement submitAppliedFilterButton;

	public FilterPopup(WebDriver driver) {
		super(driver);
	}

	public ResultPage filterByTheBestOffer(){
		bestOfferCheckBox.click();
		submitAppliedFilterButton.submit();
		return new ResultPage(driver);		
	}

}
