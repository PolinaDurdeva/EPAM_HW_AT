package com.epam.qaschool.pages;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.epam.qaschool.wrappers.ItemPrice;

public class ResultPage extends Page{
	private static final String RU_CURRENCY_STRING = "руб.";
	private static final String PART_PRISE_STRING = "до";	
	private static final String PRICE_NUMBER_FROMAT = "###.##";
	private static final String ITEMS_PRICES_XPATH = ".//*[@id='Results']//li[@class='lvprice prc']/span";
	private static final String FREE_INTERNTIONAL_SHIPPING_XPATH = "";
	private static final String COUNTRY_XPATH = ".//*[@id='Results']//li//li[contains(.,'Страна') or contains(.,'From')]";
	private static final String DESIRED_ITEMS_XPATH = "";
	
	@FindBy(xpath=ITEMS_PRICES_XPATH)
	private List<WebElement> itemPricesOnSerp;
	
	@FindBy(xpath=COUNTRY_XPATH)
	private List<WebElement> itemCountriesOnSerp;
	
	@FindBy(xpath=DESIRED_ITEMS_XPATH)
	private List<WebElement> productItemsOnSerp; 
	
	@FindBy(xpath=FREE_INTERNTIONAL_SHIPPING_XPATH)
	private List<WebElement> itemShippingConditions;

	public ResultPage(WebDriver driver) {
		super(driver);
	}
	
	public List<WebElement> getAllProductsOnSerp(){
		return productItemsOnSerp;
	}
	
	public List<ItemPrice> getProductPrices(){
		DecimalFormat priceFormat = new DecimalFormat(PRICE_NUMBER_FROMAT);
        List<ItemPrice> prices = new ArrayList<ItemPrice>();
        for (WebElement itemPrice: itemPricesOnSerp) {
        	String[] priceAsString = null;
        	try {
				priceAsString = itemPrice.getText()
						.replace(RU_CURRENCY_STRING, "")
						.replace(" ", "")
						.replace(',', '.')
						.split(PART_PRISE_STRING);
				if (priceAsString.length == 1){
					Float price = priceFormat.parse(priceAsString[0]).floatValue();
					prices.add(new ItemPrice(price));
				}else{
					Float minPrice = priceFormat.parse(priceAsString[0]).floatValue();
					Float maxPrice = priceFormat.parse(priceAsString[1]).floatValue();
					prices.add(new ItemPrice(minPrice, maxPrice));					
				}
				log.debug("Extracted item price: {}", prices.get(prices.size() - 1));
			} catch (ParseException e) {
				log.error("Price number format doesn't match with specidied format. Price: {}", itemPrice.getText());
			}
        }
        return prices;
	}
	
	public List<String> getItemsHrefs(){
        List<String> itemHrefs = new ArrayList<String>();
        for (WebElement resultItem: productItemsOnSerp) {
            String href = resultItem.getText();
            itemHrefs.add(href);
        }
        return itemHrefs;
	}
	
	public List<String> getCountriesOfProduction(){
		List<String> countries = new ArrayList<String>();
		for (WebElement itemCountry: itemCountriesOnSerp) {
			countries.add(itemCountry.getText());
		}
		return countries;
	}
	
	public List<String> getIntrShipping(){
		List<String> shippingConds = new ArrayList<String>();
		for (WebElement itemShip: itemShippingConditions){
			shippingConds.add(itemShip.getText().toLowerCase());
		}
		return shippingConds;
	}
}
