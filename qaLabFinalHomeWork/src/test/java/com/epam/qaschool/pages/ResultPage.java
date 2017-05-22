package com.epam.qaschool.pages;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.LoggerFactory;

public class ResultPage extends Page{
	private static final String PART_PRICE_STRING = "руб.";
	private static final String PRICE_NUMBER_FROMAT = "###.##";
	private static final String ITEMS_PRICES_XPATH = ".//*[@id='Results']//li[@class=\"lvprice prc\"]/span";
	private static final String FREE_INTERNTIONAL_SHIPPING_XPATH = "";
	private static final String COUNTRY_XPATH = "";
	private static final String DESIRED_ITEMS_XPATH = "";
	
	@FindBy(xpath=ITEMS_PRICES_XPATH)
	private List<WebElement> itemPricesOnSerp;
	
	@FindBy(xpath=COUNTRY_XPATH)
	private List<WebElement> itemCountriesOnSerp;
	
	@FindBy(xpath=DESIRED_ITEMS_XPATH)
	private List<WebElement> desiredItemsOnSerp; 
	
	@FindBy(xpath=FREE_INTERNTIONAL_SHIPPING_XPATH)
	private List<WebElement> itemShipping;

	public ResultPage(WebDriver driver) {
		super(driver);
	}
	
	public List<Float> getPrices(){
		DecimalFormat priceFormat = new DecimalFormat(PRICE_NUMBER_FROMAT);
        List<Float> prices = new ArrayList<Float>();
        for (WebElement itemPrice: itemPricesOnSerp) {
        	String priceAsString = null;
        	try {
				priceAsString = itemPrice.getText()
						.replace(PART_PRICE_STRING, "")
						.replace(" ", "")
						.replace(',', '.');
				Float priceAsFloat = priceFormat.parse(priceAsString).floatValue();
				log.debug("Extracted item price: {}", priceAsFloat);
				prices.add(priceAsFloat);
			} catch (ParseException e) {
				log.debug("Price number format doesn't match with specidied format. Price: {}", priceAsString);
			}
        }
        return prices;
	}
	
	public List<String> getItemsHrefs(){
        List<String> itemHrefs = new ArrayList<String>();
        for (WebElement resultItem: desiredItemsOnSerp) {
            String href = resultItem.getText();
            itemHrefs.add(href);
        }
        return itemHrefs;
	}
	
}
