package model;

public class Vehicle {

	String regNr;
	double drivenDistance;
	int maxCargoLoad;
	String model;
	String brand;
	int carID;
	boolean maintained;
	
	public Vehicle(String regNr, double drivenDistance, int maxCargoLoad, String model, String brand, int carID,
			boolean maintained) {
		this.regNr = regNr;
		this.drivenDistance = drivenDistance;
		this.maxCargoLoad = maxCargoLoad;
		this.model = model;
		this.brand = brand;
		this.carID = carID;
		this.maintained = maintained;
	}
	
	public boolean isMaintained() {
		return false;
	}
}
