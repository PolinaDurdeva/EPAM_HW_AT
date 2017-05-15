package com.epam.testschool.model;

public interface Vehicle {
	
	/**
	 * Makes a vehicle drive 
	 * @param kilometers - distance to drive
	 */
	public void drive(float kilometers);
	/** 
	 * Grants access to subsequent manipulations with vehicle
	 * @param owner - owner of the vehicle
	 * @param key - key of vehicle's owner
	 */
	public void authorize(String owner, String key);
	/**
	 * Makes a vehicle sound
	 */
	public void beep();
	/**
	 * Prints current state of a vehicle
	 */
	public void printStatus();
		
}
