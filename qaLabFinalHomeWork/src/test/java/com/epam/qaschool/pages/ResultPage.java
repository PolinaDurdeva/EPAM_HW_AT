package com.epam.qaschool.pages;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.LoggerFactory;

import com.epam.qaschool.wrappers.ItemPrice;

public class ResultPage extends Page{
	
	private final static org.slf4j.Logger log = LoggerFactory.getLogger(ResultPage.class);
	
	private static final String ITEM_PRICES_XPATH = ".//*[@id='Results']//li[@class='lvprice prc'][1]/span";
	private static final String FREE_INTRN_SHIPPING_XPATH = ".//*[@id='Results']//span[@class='ship']//span[@class='bfsp']";
	private static final String COUNTRIES_XPATH = ".//*[@id='Results']//li//li[contains(.,'Страна') or contains(.,'From')]";
	private static final String DESIRED_ITEMS_XPATH = ".//*[@id='Results']//h3/a";
	private static final String AUCTION_BIDS_XPATH = ".//*[@id='Results']//li[@class='lvformat']/span";
	private static final String BEST_PRICE_MARKERS_XPATH = ".//*[@id='Results']//li[@class='lvformat']/span";
	
	private static final String PRISE_SEPARATOR = "до";	
	private static final String RU_CURRENCY_REGEX = "руб.|RUB";
	private static final String BIDS_REGEX = "\\d+\\s[bid|став].*";
	private static final String FREE_DELIVERY_RU_TEXT = "Бесплатная международная доставка";
	private static final String FREE_DELIVERY_EN_TEXT = "Free international shipping";
	private static final String BEST_PRICE_RU_TEXT = "лучшая цена";
	private static final String BEST_PRICE_EN_TEXT = "best offer";
	private static final String PRICE_NUMBER_FROMAT = "###.##";
	private static final String ATTRIBUTE_HREF = "href";
	
	@FindBy(xpath=ITEM_PRICES_XPATH)
	private List<WebElement> itemPricesOnSerp;
	
	@FindBy(xpath=COUNTRIES_XPATH)
	private List<WebElement> itemCountriesOnSerp;
	
	@FindBy(xpath=DESIRED_ITEMS_XPATH)
	private List<WebElement> productsOnSerp; 
	
	@FindBy(xpath=FREE_INTRN_SHIPPING_XPATH)
	private List<WebElement> itemShippingConditions;
	
	@FindBy(xpath=AUCTION_BIDS_XPATH)
	private List<WebElement> itemBetsOnAuction;
	
	@FindBy(xpath=BEST_PRICE_MARKERS_XPATH)
	List<WebElement> bestPriceOffers;
	
	private DecimalFormat priceFormat = new DecimalFormat(PRICE_NUMBER_FROMAT);

	public ResultPage(WebDriver driver) {
		super(driver);
	}
	
	public List<WebElement> getAllProductsOnSerp(){
		return productsOnSerp;
	}

	public int getCountItemsOnSerp(){
		return productsOnSerp.size();
	}
	
	public List<String> getItemsHrefs(){
		List<String> itemHrefs = new ArrayList<String>();
		for (WebElement resultItem: productsOnSerp) {
			String href = resultItem.getAttribute(ATTRIBUTE_HREF);
			itemHrefs.add(href);
		}
		return itemHrefs;
	}
	
	public List<ItemPrice> getProductPrices(){
        List<ItemPrice> prices = new ArrayList<ItemPrice>();
        for (WebElement itemPrice: itemPricesOnSerp) {
        	String[] priceAsString = null;
        	try {
				priceAsString = itemPrice.getText()
						.replaceAll(RU_CURRENCY_REGEX, "")
						.replace(" ", "")
						.replace(',', '.')
						.split(PRISE_SEPARATOR);
				if (priceAsString.length == 1){
					Float price = priceFormat.parse(priceAsString[0]).floatValue();
					prices.add(new ItemPrice(price));
				}else{
					Float minPrice = priceFormat.parse(priceAsString[0]).floatValue();
					Float maxPrice = priceFormat.parse(priceAsString[1]).floatValue();
					prices.add(new ItemPrice(minPrice, maxPrice));					
				}
				log.debug("Extracted item price: {}", prices.get(prices.size() - 1));
			} catch (ParseException numberFormatException) {
				log.error("Price number format doesn't match with specidied format. Price: {}", itemPrice.getText());
			}
        }
        return prices;
	}
	
	public List<String> getCountriesOfProduction(){
		List<String> countries = new ArrayList<String>();
		for (WebElement itemCountry: itemCountriesOnSerp) {
			countries.add(itemCountry.getText());
			log.debug("Country of manufactory: {}", itemCountry.getText());
		}
		return countries;
	}
	
	public List<String> getIntrShippingMarkers(){
		List<String> shippingConds = new ArrayList<String>();
		for (WebElement itemShip: itemShippingConditions){
			String itemShipText = itemShip.getText();
			if (itemShipText.equalsIgnoreCase(FREE_DELIVERY_EN_TEXT) || 
					itemShipText.equalsIgnoreCase(FREE_DELIVERY_RU_TEXT)){				
				shippingConds.add(itemShipText);
			}
			log.debug("Shipping condition: {}", itemShip.getText());
		}
		return shippingConds;
	}

	public List<String> getBets(){
		List<String> itemBets = new ArrayList<String>();
		for (WebElement bet : itemBetsOnAuction) {
			if (bet.getText().matches(BIDS_REGEX)){
				itemBets.add(bet.getText());				
			}
			log.debug("Count of bets on auction: {}", bet.getText());
		}
		return itemBets;
	}
	
	public List<String> getBestPriceMarkers(){
		List<String> bestPrices = new ArrayList<String>();
		for (WebElement offer : bestPriceOffers) {
			String offerText = offer.getText().toLowerCase();
			if (offerText.contains(BEST_PRICE_EN_TEXT) || offerText.contains(BEST_PRICE_RU_TEXT)){
				bestPrices.add(offerText);				
			}
			log.debug("The best price offer: {}",offerText);
		}
		return bestPrices;
	}
}
