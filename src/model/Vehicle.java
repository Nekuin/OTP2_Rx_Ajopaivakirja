package model;

import javax.persistence.*;

@Entity
@Table(name="Vehicles")
public class Vehicle implements IVehicle {

	@Id
	@Column(name="carID")
	private int carID;
	
	@Column(name="regnr")
	private String regNr;
	
	@Column(name="drivendistance")
	private double drivenDistance;
	
	@Column(name="maxcargoload")
	private int maxCargoLoad;
	
	@Column(name="model")
	private String model;
	
	@Column(name="brand")
	private String brand;
	
	@Column(name="maintained")
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
	
	@Override
	public boolean isMaintained() {
		return maintained;
	}

	@Override
	public String getRegNr() {
		return regNr;
	}

	@Override
	public void setRegNr(String regNr) {
		this.regNr = regNr;
	}

	@Override
	public double getDrivenDistance() {
		return drivenDistance;
	}

	
	public void setDrivenDistance(double drivenDistance) {
		this.drivenDistance = drivenDistance;
	}
	
	@Override
	public void addDrivenDistance(double driven) {
		this.drivenDistance += driven;
	}

	@Override
	public int getMaxCargoLoad() {
		return maxCargoLoad;
	}

	@Override
	public void setMaxCargoLoad(int maxCargoLoad) {
		this.maxCargoLoad = maxCargoLoad;
	}

	@Override
	public String getModel() {
		return model;
	}

	@Override
	public void setModel(String model) {
		this.model = model;
	}

	@Override
	public String getBrand() {
		return brand;
	}

	@Override
	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public int getCarID() {
		return carID;
	}

	@Override
	public void setCarID(int carID) {
		this.carID = carID;
	}

	@Override
	public boolean getMaintained() {
		return maintained;
	}

	@Override
	public void setMaintained(boolean maintained) {
		this.maintained = maintained;
	}
}
