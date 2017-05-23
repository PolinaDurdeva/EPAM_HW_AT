package com.epam.qaschool.wrappers;


public class ItemPrice {	
	private float maxPrice;
	private float minPrice;
	
	public ItemPrice(float minPrice, float maxPrice) {
		this.maxPrice = maxPrice;
		this.minPrice = minPrice;
	}
	
	public ItemPrice(float price) {
		this.maxPrice = price;
		this.minPrice = price;
	}
	
	@Override
	public String toString() {
		return "ItemPrice [maxPrice=" + maxPrice + ", minPrice=" + minPrice	+ "]";
	}

	public float getMaxPrice() {
		return maxPrice;
	}
	
	public float getMinPrice() {
		return minPrice;
	}
}
