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
		//TODO: implement
		
	}
	
	public void removeDriver(int employeeID) {
		//TODO: implement
	}
	
	public void addDrivingShift(int shiftID, String startTime, String finishTime, Driver shiftDriver) {
		//DrivingShift shift = new DrivingShift(startTime, finishTime, shiftDriver);
	}
	
	public void removeDrivingShift(int shiftID) {
		
	}

}
