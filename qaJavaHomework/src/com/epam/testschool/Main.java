package com.epam.testschool;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.epam.testschool.annotations.SaleInfoPrinter;
import com.epam.testschool.model.Bicycle;
import com.epam.testschool.model.Car;
import com.epam.testschool.model.Vehicle;
import com.epam.testschool.model.VehicleType;

public class Main {

	public static void main(String[] args) {
		demoOfBikesSorting();
		demoOfCarManipulation();
	}

	/**
	 * This method creates three different test bicycle objects
	 *  and demonstrates sorting process by anonymous comparator.
	 */
	private static void demoOfBikesSorting() {
		System.out.println("Demonstration of sorting bycicle objects by field using anonymous comparator.");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -1);
		Date oneYearAgo  = calendar.getTime();
		calendar.add(Calendar.YEAR, -1);
		Date twoYearsAgo = calendar.getTime();
		calendar.add(Calendar.YEAR, -1);
		Date threeYearsAgo = calendar.getTime();
		List<Bicycle> bikes = new ArrayList<Bicycle>();
		bikes.add(new Bicycle("Alexey",  oneYearAgo,   "brutalMonsterBike"));
		bikes.add(new Bicycle("Tatiana", twoYearsAgo,  "prettyNitroBike"));
		bikes.add(new Bicycle("Anton",   threeYearsAgo,  "superFastTurboBike"));
		System.out.println("Ininital: " + bikes);
		bikes.sort(new Comparator<Bicycle>() {
			@Override
			public int compare(Bicycle o1, Bicycle o2) {
				return o1.getDateOfManufacture().compareTo(o2.getDateOfManufacture());
			}
		});
		System.out.println("Sorted by date asc:" + bikes);
	}

	
	/**
	 * Creates a car object and demonstrates usage of provided methods.
	 * Also this method is used for checking whether annotations work correctly.
	 */
	private static void demoOfCarManipulation() {
		System.out.println("Demonstration of manipulating car object. Testing annotations");
		String owner    = "Polina";
		int mileage     = 0;
		int litersPerKm = 10;
		String carId    = "Bugatti";
		float capacity  = 100;
		int currentPetrolLevel = 25;
		String key = new StringBuilder(carId).reverse().toString(); // super secret!
		int kilometersFromHomeToEPAM = 5;
		Car myCar = new Car(owner,
							VehicleType.PASSENGER,
							new Date(),
							mileage,
							litersPerKm,
							carId,
							capacity,
							currentPetrolLevel		
				);
		myCar.addPetrol(25);
		myCar.printStatus();
		myCar.authorize(owner, key);
		myCar.drive(kilometersFromHomeToEPAM);
		myCar.printStatus();
		myCar.beep();
		executeAnnotatedMethod(myCar);
	}

	private static void executeAnnotatedMethod(Vehicle vehicle) {
		for (Method method : vehicle.getClass().getMethods()){
			if (method.isAnnotationPresent(SaleInfoPrinter.class)){
				try {
					method.invoke(vehicle);
				} catch (IllegalAccessException | IllegalArgumentException	| InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
