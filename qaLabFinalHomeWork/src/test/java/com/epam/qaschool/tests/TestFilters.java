package com.epam.qaschool.tests;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.qaschool.base.TestNgTestBase;
import com.epam.qaschool.data.DataForTest;
import com.epam.qaschool.pages.HomePage;
import com.epam.qaschool.pages.LeftFilterPannel;
import com.epam.qaschool.pages.ResultPage;
import com.epam.qaschool.pages.SearchForm;
import com.epam.qaschool.wrappers.ItemPrice;

import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.assertTrue;


public class TestFilters extends TestNgTestBase{
	private static final int COUNT_ITEMS = 50;
	private static final String US_RU = "США";
	private static final String US_EN = "United States";
	private static final String FREE_SHIPPING_RU = "Бесплатная международная доставка";
	private static final String FREE_SHIPPING_EN = "Free international shipping";
	private HomePage homePage;
	private LeftFilterPannel leftFilterPannel;
	private SearchForm searchForm;
	private ResultPage resultPage;
	
	@BeforeMethod
	public void setUpHomePage(){
		homePage = new HomePage(driver);
		homePage.openPage(baseUrl);		
	}
	
	
	@BeforeMethod
	public void initializeSearchForm(){
		searchForm = new SearchForm(driver);
	}

	@BeforeMethod
	public void initializeLeftFilterPannel() {
		leftFilterPannel = new LeftFilterPannel(driver);		
	}
	
	@Test(dataProvider="requestsProductSearch", dataProviderClass=DataForTest.class)
	public void testFiltersOnItemsPrice(String request, float leftBound, float rightBound){
		searchForm.searchFor(request);
		List<ItemPrice> itemPrices= leftFilterPannel.filterByPriceRange(leftBound,rightBound).getProductPrices();
		//assertThat(itemsPrices).hasSize(COUNT_ITEMS);
		for (ItemPrice price: itemPrices) {
			assertTrue(
					(price.getMaxPrice() <= rightBound && price.getMaxPrice() >= leftBound) ||
					(price.getMinPrice() <= rightBound && price.getMinPrice() >= leftBound)
					);
		}
	}

	@Test(dataProvider="requestsProduct", dataProviderClass=DataForTest.class)
	public void testFiltersOnCountryOfProduction(String request){
		searchForm.searchFor(request);		
		List<String> itemCountries = leftFilterPannel.filterByCountryOptionsUSOnly().getCountriesOfProduction();
		for (String country: itemCountries){
			assertTrue(country.contains(US_EN) || country.contains(US_RU), 
					String.format("Expected %s or %s, but \n %s", US_EN, US_RU, country));
		}
	}
	
	@Test(dataProvider="requestsProduct", dataProviderClass=DataForTest.class)
	public void testFilterOnFreeShipping(String request){
		searchForm.searchFor(request);
		List<String> itemShipping = leftFilterPannel.filterByFreeShipping().getIntrShipping();
		assertThat(itemShipping).hasSize(COUNT_ITEMS);
	}
}
