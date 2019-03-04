package model;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class HrManager extends Employee{
	
	@Transient
	private DriverAccessObject a;
	
	@Transient
	private DrivingShiftAO ao;
	
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
	 * Sets the access object for driver objects
	 * @param a driver access object
	 */
	public void setAjoAccessObject(DriverAccessObject a) {
		this.a = a;
	}
	
	
	/**
	 * Sets the access object for driving shift objects
	 * @param ao driving shift access object
	 */
	public void setDrivingShiftAO(DrivingShiftAO ao) {
		this.ao = ao;
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
	
	
	/**
	 * Adds driving shift to the database
	 * @param client client of the driving shift
	 * @param cargo cargo of the driving shift
	 */
	public void addDrivingShift(Client client, Cargo cargo) {
		DrivingShift shift = new DrivingShift(client, cargo);
		ao.createDrivingShift(shift);
	}
	
	
	/**
	 * Removes driving shift from the database
	 * @param shift shift that is about to be removed
	 */
	public void removeDrivingShift(DrivingShift shift) {
		ao.delete(shift);
	}

}
