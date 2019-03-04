package model;

import javax.persistence.*;

@Entity
@Table(name="Vehicles")
public class Vehicle {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	
	/**
	 * Constructor for the vehicle
	 * @param regNr register number of the vehicle
	 * @param drivenDistance distance that has been driven with the vehicle
	 * @param maxCargoLoad maximum load of the vehicle
	 * @param model model of the vehicle
	 * @param brand brand of the vehicle
	 * @param maintained tells whether the vehicle has been maintained or not
	 */
	public Vehicle(String regNr, double drivenDistance, int maxCargoLoad, String model, String brand,
			boolean maintained) {
		this.regNr = regNr;
		this.drivenDistance = drivenDistance;
		this.maxCargoLoad = maxCargoLoad;
		this.model = model;
		this.brand = brand;
		this.maintained = maintained;
	}
	
	/**
	 * Empty constructor for the vehicle
	 */
	public Vehicle() {
		
	}

	
	/**
	 * Returns the register number of the vehicle
	 * @return
	 */
	public String getRegNr() {
		return regNr;
	}

	/**
	 * Sets register number of the vehicle
	 * @param regNr register number of the vehicle
	 */
	public void setRegNr(String regNr) {
		this.regNr = regNr;
	}

	/**
	 * Returns driven distance of the vehicle
	 * @return
	 */
	public double getDrivenDistance() {
		return drivenDistance;
	}

	/**
	 * Sets driven distance of the vehicle
	 * @param drivenDistance
	 */
	public void setDrivenDistance(double drivenDistance) {
		this.drivenDistance = drivenDistance;
	}

	
	/**
	 * Increases driven distance of the vehicle
	 * @param driven
	 */
	public void addDrivenDistance(double driven) {
		this.drivenDistance += driven;
	}

	
	/**
	 * Returns maximum load of the vehicle
	 * @return
	 */
	public int getMaxCargoLoad() {
		return maxCargoLoad;
	}

	/**
	 * Sets maximum load of the vehicle
	 * @param maxCargoLoad maximum load of the vehicle
	 */
	public void setMaxCargoLoad(int maxCargoLoad) {
		this.maxCargoLoad = maxCargoLoad;
	}

	/**
	 * Returns model of the vehicle
	 * @return
	 */
	public String getModel() {
		return model;
	}

	
	/**
	 * Sets model of the vehicle
	 * @param model model of the vehicle
	 */
	public void setModel(String model) {
		this.model = model;
	}

	
	/**
	 * Returns brand of the vehicle
	 * @return
	 */
	public String getBrand() {
		return brand;
	}

	
	/**
	 * Sets brand of the vehicle
	 * @param brand brand of the vehicle
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	
	/**
	 * Returns id of the vehicle
	 * @return
	 */
	public int getCarID() {
		return carID;
	}

	
	/**
	 * Sets id of the vehicle
	 * @param carID id of the vehicle
	 */
	public void setCarID(int carID) {
		this.carID = carID;
	}

	
	/**
	 * Returns whether the vehicle is maintained or not
	 * @return
	 */
	public boolean getMaintained() {
		return maintained;
	}

	
	/**
	 * Sets whether the vehicle is maintained or not
	 * @param maintained tells whether the vehicle is maintained or not
	 */
	public void setMaintained(boolean maintained) {
		this.maintained = maintained;
	}
	
	public String toString() {
		return this.brand + " " + this.model + " "  + this.regNr; 
	}
}
