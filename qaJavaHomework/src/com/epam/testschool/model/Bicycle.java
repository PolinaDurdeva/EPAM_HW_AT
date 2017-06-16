package com.epam.testschool.model;

import java.util.Date;

/**
 * @class Bicycle implements corresponding logic for bicycle
 *
 */
public class Bicycle extends AbstractVehicle{

	private static final String SIGNAL = "DZIN-DZIN";

	public Bicycle(String owner, Date dateOfManufacture, String vehicleId) {
		super(owner, VehicleType.ENGINELESS, dateOfManufacture, vehicleId);
	}

	@Override
	public void drive(float kilometers) {
		ensureAuthorization();
		System.out.println("Riding "+ kilometers + "km");
	}
	
	@Override
	public void beep() {
		System.out.println(SIGNAL);
	}
	
	@Override
	protected boolean checkCredetentials(String owner, String key) {
		 return this.owner.equals(owner) && key.toUpperCase().equals(vehicleId);
	}

	@Override
	public void printStatus() {
		System.out.println(new StringBuilder("Bicycle id: ").append(vehicleId).append("\n")
							.append("Owner ").append(owner).append("\n").toString());
	}
	
}
