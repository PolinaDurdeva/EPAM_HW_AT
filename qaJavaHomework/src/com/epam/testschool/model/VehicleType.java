package com.epam.testschool.model;

public enum VehicleType {
	ENGINELESS,
	SPORT{
		public int getMaxAllowedSpeed(){
			return SPEED_LIMIT_KPH;
		}
	},
	TRUCK,
	JEEP,
	PASSENGER{
		public boolean checkPassengersCount(int countPassengers){
			return countPassengers < LIMIT_PASSENGERS_COUNT;
		}
	};
	private static final int LIMIT_PASSENGERS_COUNT = 5;
	private static final int SPEED_LIMIT_KPH = 600;
	public void aim(){
		switch (this) {
		case TRUCK:
			System.out.println("Transportation");
			break;
		case SPORT:
			System.out.println("Sport racing");
			break;
		case JEEP:
			System.out.println("Offroad driving");
			break;
		case PASSENGER:
			System.out.println("Comfort driving");
			break;
		case ENGINELESS:
			System.out.println("Ecological driving");
			break;			
		default:
			break;
		}
	}
}