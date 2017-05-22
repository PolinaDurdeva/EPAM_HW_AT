package com.epam.qaschool.data;

import org.testng.annotations.DataProvider;

public class DataForTest {
	
	@DataProvider
	public static Object[][] requestsProductSearch() {
		return new Object[][] { 
						{"стабилизатор", 5000, 6000}
						//{"бейзболка", 350, 400}, 
						//{"кошилек", 200.55, 900.9} 
		};
	}
}
