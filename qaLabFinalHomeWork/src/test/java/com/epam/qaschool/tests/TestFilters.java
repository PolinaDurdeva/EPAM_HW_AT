package com.epam.qaschool.tests;

import java.util.Iterator;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.qaschool.base.TestNgTestBase;
import com.epam.qaschool.data.DataForTest;
import com.epam.qaschool.pages.HomePage;
import com.epam.qaschool.pages.LeftFilterPannel;
import com.epam.qaschool.pages.ResultPage;
import com.epam.qaschool.pages.SearchForm;

import static org.assertj.core.api.Assertions.*;


public class TestFilters extends TestNgTestBase{
	
	private static final int COUNT_ITEMS = 50;
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
	
	@Test(dataProvider="requestsProductSearch", dataProviderClass=DataForTest.class)
	public void testRestrictionOnItemsPrice(String request, float leftBound, float rightBound){
		resultPage = searchForm.searchFor(request);
		leftFilterPannel = new LeftFilterPannel(driver);
		List<Float> itemsPrices= leftFilterPannel.filterByPriceRange(leftBound,rightBound).getPrices();
		//assertThat(itemsPrices).hasSize(COUNT_ITEMS);
		for (Float price: itemsPrices) {
			assertThat(price).isBetween(leftBound, rightBound);
		}
	}
}
