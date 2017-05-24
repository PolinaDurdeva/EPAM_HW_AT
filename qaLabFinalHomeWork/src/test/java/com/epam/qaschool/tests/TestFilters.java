package com.epam.qaschool.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.qaschool.base.TestNgTestBase;
import com.epam.qaschool.data.DataForTest;
import com.epam.qaschool.pages.FilterPopup;
import com.epam.qaschool.pages.HomePage;
import com.epam.qaschool.pages.LeftFilterPannel;
import com.epam.qaschool.pages.ProductPage;
import com.epam.qaschool.pages.ResultPage;
import com.epam.qaschool.pages.SearchForm;
import com.epam.qaschool.wrappers.ItemPrice;

public class TestFilters extends TestNgTestBase{
	private static final String US_RU = "США";
	private static final String US_EN = "United States";
	
	private HomePage homePage;
	private LeftFilterPannel leftFilterPannel;
	private SearchForm searchForm;
	
	@BeforeMethod
	public void initializePages(){
		homePage = new HomePage(driver);
		homePage.openPage(baseUrl);
		searchForm = new SearchForm(driver);
		leftFilterPannel = new LeftFilterPannel(driver);
	}
	
	/**
	 * Test that filters by bounds of price actually restricts product's price on SERP
	 * @param request - search request
	 * @param leftBound - left bound of price
	 * @param rightBound - right bound of price
	 */
	@Test(dataProvider="requestsProductSearch", dataProviderClass=DataForTest.class)
	public void testFiltersByItemsPrice(String request, float leftBound, float rightBound){
		searchForm.searchFor(request);
		List<ItemPrice> itemPrices= leftFilterPannel.filterByPriceRange(leftBound,rightBound).getProductPrices();
		for (ItemPrice price: itemPrices) {
			 // Some product prices have two values: left bound and right bound.
			 // In this case it tests that some of bounds is between specified bounds   
			assertTrue(
						(price.getMaxPrice() <= rightBound && price.getMaxPrice() >= leftBound) ||
						(price.getMinPrice() <= rightBound && price.getMinPrice() >= leftBound)
					);
		}
	}

	/**
	 * Tests that products are manufactured in US
	 * @param request
	 */
	@Test(dataProvider="requestsProduct", dataProviderClass=DataForTest.class)
	public void testFiltersByMadeInUSOnly(String request){
		searchForm.searchFor(request);		
		List<String> itemCountries = leftFilterPannel.filterByCountryOptionsUSOnly().getCountriesOfProduction();
		for (String country: itemCountries){
			assertTrue(country.contains(US_EN) || country.contains(US_RU), 
					String.format("Expected %s or %s, but \n %s", US_EN, US_RU, country));
		}
	}
	
	@Test(dataProvider="requestsProduct", dataProviderClass=DataForTest.class)
	public void testFilterByFreeShipping(String request){
		searchForm.searchFor(request);
		ResultPage filtered = leftFilterPannel.filterByFreeShipping();
		int countItems = filtered.getCountItemsOnSerp();
		assertThat(filtered.getIntrShippingMarkers()).hasSize(countItems);
	}
	
	@Test(dataProvider="requestsProduct", dataProviderClass=DataForTest.class)
	public void testFilterByAuction(String request){
		searchForm.searchFor(request);
		ResultPage filtered = leftFilterPannel.filterByAuction();
		int countItems = filtered.getCountItemsOnSerp();
		assertThat(filtered.getBets()).hasSize(countItems);
	}
	
	@Test(dataProvider="requestsProduct", dataProviderClass=DataForTest.class)
	public void testFilterByBestPrice(String request){
		searchForm.searchFor(request);
		FilterPopup popup = leftFilterPannel.getFilterPopupShowOnlyTab();
		ResultPage filtered = popup.filterByTheBestOffer();
		int countItems = filtered.getCountItemsOnSerp();
		List<String> bestPriceOffers = filtered.getBestPriceMarkers();
		assertThat(bestPriceOffers).hasSize(countItems);
	}
	
	@Test(dataProvider="requestsProduct", dataProviderClass=DataForTest.class)
	public void testFilterByUsedState(String request){
		searchForm.searchFor(request);
		ResultPage results = leftFilterPannel.filterByUsedState();
		for (String href : results.getItemsHrefs()) {
			driver.get(href);
			assertTrue(new ProductPage(driver).isUsedState());
		}
	}
	
	/**
	 * Test that filter by "Returns accepted" returns goods which can be refunded
	 * @param request
	 */
	@Test(dataProvider="requestsProduct", dataProviderClass=DataForTest.class)
	public void testFilterByReturnConditions(String request){
		searchForm.searchFor(request);
		ResultPage results = leftFilterPannel.filterByReturnOption();
		for (String href : results.getItemsHrefs()) {
			driver.get(href);
			assertTrue(new ProductPage(driver).isWithReturnCondition());
		}
	}
}
