package model;

import javax.persistence.*;

@Entity
@Table(name="Vehicles")
public class Vehicle {

	@Id
	@Column(name="regnr")
	private String regNr;
	
	@Column(name="drivendistance")
	private double drivenDistance;
	
	@Column(name="maxcargoload")
	private int maxCargoLoad;
	private String model;
	private String brand;
	private int carID;
	private boolean maintained;
	
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
		return maintained;
	}

	public String getRegNr() {
		return regNr;
	}

	public void setRegNr(String regNr) {
		this.regNr = regNr;
	}

	public double getDrivenDistance() {
		return drivenDistance;
	}

	public void setDrivenDistance(double drivenDistance) {
		this.drivenDistance = drivenDistance;
	}

	public int getMaxCargoLoad() {
		return maxCargoLoad;
	}

	public void setMaxCargoLoad(int maxCargoLoad) {
		this.maxCargoLoad = maxCargoLoad;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getCarID() {
		return carID;
	}

	public void setCarID(int carID) {
		this.carID = carID;
	}

	public boolean getMaintained() {
		return maintained;
	}
	
	public void setMaintained(boolean maintained) {
		this.maintained = maintained;
	}
}
