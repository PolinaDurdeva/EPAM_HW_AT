package com.epam.qaschool.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.LoggerFactory;

public class ProductPage extends Page{
	
	private final static org.slf4j.Logger log = LoggerFactory.getLogger(ProductPage.class);
	
	private static final String POSSIBILITY_OF_REFUND_XPATH = ".//*[@id='vi-ret-accrd-txt']";
	private static final String PRODUCT_STATE_XPATH         = ".//*[@id='vi-itm-cond']";
	private static final String RETURN_COND                 = ".*[0-9]+\\s[дн|days].*";
	private static final String USED_RU_TEXT                = "б/у";
	private static final String USED_EN_TEXT                = "used";
	private static final String REPAIRED_RU_TEXT            = "восстановлен";
	private static final String REPAIRED_EN_TEXT            = "refurbished";
	
	
	
	@FindBy(xpath=POSSIBILITY_OF_REFUND_XPATH)
	private WebElement returns;
	
	@FindBy(xpath=PRODUCT_STATE_XPATH)
	private WebElement productState;
	
	public ProductPage(WebDriver driver) {
		super(driver);
	}
	
	public boolean isWithReturnCondition(){
		log.debug(returns.getText());
		return returns.getText().matches(RETURN_COND);
	}
	
	public boolean isUsedState() {
		String state = productState.getText().toLowerCase();
		log.debug("State of product: {} {} ,{}", state);
		return state.contains(USED_RU_TEXT) || 
				state.contains(USED_EN_TEXT) ||
				state.contains(REPAIRED_EN_TEXT) ||
				state.contains(REPAIRED_RU_TEXT);
	}

}
