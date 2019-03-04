package model;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class HrManager extends Employee{
	
	@Transient
	private DriverAccessObject a;
	
	/**
	 * Constructor for the hr manager
	 * @param name name of the hr manager
	 */
	public HrManager(String name) {
		super(name);
	}
	
	
	/**
	 * Empty constructor for HrManager
	 */
	public HrManager() {
		super();
	}
	
	
	/**
	 * Sets the access object that is used with database
	 * @param a driver access object
	 */
	public void setAjoAccessObject(DriverAccessObject a) {
		this.a = a;
	}
	
	/**
	 * Add driver to the database
	 * @param name name of the driver
	 * @param driversLicense drivers license of the driver
	 */
	public void addDriver(String name, String driversLicense) {
		Driver driver = new Driver(name,driversLicense);
		a.create(driver);	
	}
	
	/**
	 * Removes driver from the database
	 * @param driver driver that is about to be removed
	 */
	public void removeDriver(Driver driver) {
		a.delete(driver);
	}
	
	public void addDrivingShift(Client client, Cargo cargo) {
		DrivingShift shift = new DrivingShift(client, cargo);
		//TODO
	}
	
	public void removeDrivingShift(DrivingShift shift) {
		//TODO
	}

}
