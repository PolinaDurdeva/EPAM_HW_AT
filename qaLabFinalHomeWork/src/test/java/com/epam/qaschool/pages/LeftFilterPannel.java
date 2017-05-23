package com.epam.qaschool.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LeftFilterPannel extends Page{
	private static final String FREE_SHIPPING_INPUT_XPATH  =".//input[@name='LH_DeliveryOptions']";
	private static final String LEFT_PRICE_INPUT_XPATH =".//input[@name='_udlo']";
	private static final String RIGHT_PRICE_INPUT_XPATH =".//input[@name='_udhi']";
	private static final String US_ONLY_INPUT_XPATH = ".//input[@name='LH_PrefLoc' and ./following-sibling::span[contains(.,'US') or contains(.,'США')]]";
	private static final String RETURN_OPTION_INPUT_XPATH = ".//input[@name=\"LH_ShowOnly\" and ./following-sibling::span[contains(.,'возврат') or contains(.,'Return')]]";
	private static final String USED_STATE_INPUT_XPATH = ".//input[@name='LH_ItemCondition' and ./following-sibling::span[contains(.,'Б/у') or contains(.,'Used')]]";
	private static final String AUCTION_INPUT_XPATH = ".//input[@name='LH_BuyingFormats' and @value='LH_Auction']";
	private static final String SET_PRICE_BTN_XPATH = ".//form[@name='price']/input[@type='submit']";
	private static final String SEE_ALL_SHOW_ONLY_XPATH = ".//span[@id='LH_ShowOnly']/following-sibling::span[@class='seeall']/button";
		
	@FindBy(xpath=FREE_SHIPPING_INPUT_XPATH)
	WebElement freeShippingCheckBox;
	
	@FindBy(xpath=US_ONLY_INPUT_XPATH)
	WebElement countryUSCheckBox;
	
	@FindBy(xpath=RETURN_OPTION_INPUT_XPATH)
	WebElement returnOptionCheckBox;
	
	@FindBy(xpath=USED_STATE_INPUT_XPATH)
	WebElement usedStateCheckBox;
	
	@FindBy(xpath=AUCTION_INPUT_XPATH)
	WebElement auctionCheckBox;
	
	@FindBy(xpath=LEFT_PRICE_INPUT_XPATH)
	WebElement leftBoundPriceTextInput;
	
	@FindBy(xpath=RIGHT_PRICE_INPUT_XPATH)
	WebElement rightBoundPriceTextInput;
	
	@FindBy(xpath=SET_PRICE_BTN_XPATH)
	WebElement setPriceButton;
	
	@FindBy(xpath=SEE_ALL_SHOW_ONLY_XPATH)
	WebElement seeAllShowOnlyBtn;
	
	public LeftFilterPannel(WebDriver driver) {
		super(driver);
	}
	
	public ResultPage filterByPriceRange(float leftBound, float rightBound){
		leftBoundPriceTextInput.clear();
		rightBoundPriceTextInput.clear();
		leftBoundPriceTextInput.sendKeys(Float.toString(leftBound));
		rightBoundPriceTextInput.sendKeys(Float.toString(rightBound));
		setPriceButton.click();
		return new ResultPage(driver);
	}

	public ResultPage filterByCountryOptionsUSOnly(){
		countryUSCheckBox.click();
		return new ResultPage(driver);
	}
	
	public ResultPage filterByFreeShipping(){
		freeShippingCheckBox.click();
		return new ResultPage(driver);
	}

	public ResultPage filterByAuction(){
		auctionCheckBox.click();
		return new ResultPage(driver);
	}

	public ResultPage filterByUsedState(){
		usedStateCheckBox.click();
		return new ResultPage(driver);
	}

	public ResultPage filterByReturnOption(){
		returnOptionCheckBox.click();
		return new ResultPage(driver);
	}
	
 	public FilterPopup getFilterPopupShowOnlyTab(){
		seeAllShowOnlyBtn.click();
		return new FilterPopup(driver);
	}
	
}
