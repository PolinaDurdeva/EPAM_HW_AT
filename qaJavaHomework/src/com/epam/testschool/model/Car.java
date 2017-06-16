package com.epam.testschool.model;

import java.lang.reflect.Field;
import java.util.Date;

import com.epam.testschool.annotations.SaleInfo;
import com.epam.testschool.annotations.SaleInfoPrinter;
import com.epam.testschool.exceptions.IllegalFuelRequestException;

public class Car extends AbstractVehicle{
	private static final String SIGNAL = "BEEEP";
	@SaleInfo
	private float mileage;
	@SaleInfo
	private float litersPerKm;
	private PetrolTank tank;

	/**
	 * @param owner
	 * @param type
	 * @param dateOfManufacture
	 * @see AbstractVehicle
	 * @param mileage - number of kilometers which the car has driven since date of manufacture
	 * @param litersPerKm - petrol consumption
	 * @param carId - car's identificator
	 * @param capacity - capacity of petroltank
	 * @param currentPetrolLevel - current percentage of fulfillment of petroltank  
	 */
	public Car(String owner, VehicleType type, Date dateOfManufacture, float mileage,
			float litersPerKm, String carId, float capacity, float currentPetrolLevel) {
		super(owner, type, dateOfManufacture, carId);
		this.mileage = mileage;
		this.litersPerKm = litersPerKm;
		this.tank = new PetrolTank(capacity, currentPetrolLevel);
	}

	private class PetrolTank {
		private float capacity;
		private float currentPercentLevel;
		public PetrolTank(float capacity, float currentLevel) {
			this.capacity = capacity;
			this.currentPercentLevel = currentLevel;
		}
		public boolean refuel(float liters){
			float litersPercent = toPercents(liters);
			try {
				ensureChangeCapacity(litersPercent);
				this.currentPercentLevel += litersPercent;
				return true;
			} catch (IllegalFuelRequestException e) {
				return false;
			}
		}
		public boolean consumeForDistance(float kmDistance){
			float consuming = kmDistance * Car.this.litersPerKm;
			float percents = toPercents(consuming);
			try {
				ensureChangeCapacity(-percents);
				this.currentPercentLevel -= percents;
				return true;
			} catch (IllegalFuelRequestException e) {
				e.printStackTrace();
				return false;
			}
		}
		private void ensureChangeCapacity(float percentDelta) throws IllegalFuelRequestException {
			float expectedPercent = percentDelta + currentPercentLevel;
			if ( expectedPercent > 100 && expectedPercent < 0) {
				throw new IllegalFuelRequestException(capacity * percentDelta);
			}
		}
		private float toPercents(float liters) {
			return (liters / capacity) * 100;
		}
	}

	@Override
	public void drive(float kilometers) {
		ensureAuthorization();
		mileage += kilometers;
		tank.consumeForDistance(kilometers);
		System.out.println("Drove " + kilometers + " km.");
	}

	@Override
	public void beep() {
		System.out.println(SIGNAL);
	}

	/**
	 * Adds some petrol to the tank
	 * @param liters - amount of liters to add
	 */
	public void addPetrol(float liters){
		tank.refuel(liters);
	}

	@Override
	protected boolean checkCredetentials(String owner, String key) {
		String hashKey  = new StringBuilder(key).reverse().toString();
		return hashKey.equals(vehicleId) && this.owner.equals(owner);
	}


	/** 
	 * Prints actual state of car: {@link AbstractVehicle#vehicleId}, {@link AbstractVehicle#owner}, 
	 * {@link AbstractVehicle#dateOfManufacture}, {@link Car#mileage}, 
	 * current percentage of fulfillment of petroltank
	 */
	@Override
	public void printStatus() {
		String carInfo = new StringBuilder("----------").append("\n")
				.append("Car: ").append(vehicleId).append("\n")
				.append("Owner: ").append(owner).append("\n")
				.append("Date of manufacture: ").append(dateOfManufacture).append("\n")
				.append("Mileage: ").append(mileage).append("\n")
				.append("Petrol level: ").append(tank.currentPercentLevel).append("%\n")
				.append("----------")
				.toString();
		System.out.println(carInfo);
	}


	/**
	 * Prints some necessary information for selling this car which was annotated {@link SaleInfo} 
	 * in the current class and the super class  
	 */
	@SaleInfoPrinter
	public void printSaleInfo() {
		StringBuilder builder = new StringBuilder("Sale info:").append("\n");
		Field[] fields = this.getClass().getDeclaredFields();
		Field[] superFields = this.getClass().getSuperclass().getDeclaredFields();
		Field[] allFields = new Field[superFields.length + fields.length];
		System.arraycopy(superFields, 0, allFields, 0, superFields.length);
		System.arraycopy(fields, 0, allFields, superFields.length, fields.length);
		for (Field field : allFields) {
			if (field.isAnnotationPresent(SaleInfo.class)) {
				try {
					builder
					.append(field.getName()).append("=").append(field.get(this))
					.append("\n");
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(builder.toString());
	}
	
	/**
	 * Used just for having an analogue for {@link printSaleInfo}: 
	 * prints some unreal information about car
	 */
	public void printFakeSaleInfo() {
		StringBuilder builder = new StringBuilder("Sale info:")
		.append("\n")
		.append("Milage").append("=").append(Math.max(0, this.mileage / 2)).append("\n")
		.append("Date").append("=").append(new Date()).append("\n")
		.append("LitersPerKm").append("=").append("0.000001.").append("\n");
		System.out.println(builder.toString());
	}
	
	public float getMileage() {
		return mileage;
	}
	
	public void setMileage(float mileage) {
		this.mileage = mileage;
	}
	
	public float getLitersPerKm() {
		return litersPerKm;
	}
	
	public void setLitersPerKm(float litersPerKm) {
		this.litersPerKm = litersPerKm;
	}
}
