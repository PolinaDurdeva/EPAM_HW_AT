package com.epam.testschool.exceptions;

/**
 * Thrown to indicate that inconsistent amount of liters is requested from vehicle's petroltank
 *
 */
public class IllegalFuelRequestException extends Exception{
	private float liters;
	public IllegalFuelRequestException(float liters) {
		this.liters = liters;
	}
	@Override
	public String getMessage() {
		return "Illegal amount of petrol is requested: " + liters; 
	}
}
