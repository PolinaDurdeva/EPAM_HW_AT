package com.epam.qaschool.data;

import org.testng.annotations.DataProvider;

public class DataForTest {
	
	@DataProvider
	public static Object[][] requestsProductSearch() {
		return new Object[][] { 
						{"стабилизатор", 5000.54, 6120.9},
						{"наушники",     350,     499}, 
						{"термос",       300,     600} 
		};
	}
	
	@DataProvider
	public static Object[][] requestsProduct() {
		return new Object[][] { 
				{"стабилизатор"},
				{"power bank"},
				{"mp3"}
		};
	}
}
