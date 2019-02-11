package model;

public class HrManager extends Employee {
	
	private AjoAccessObject a;

	/**
	 * Constructor for then hr manager
	 * @param name name of the hr manager
	 * @param employeeID id of the hr manager
	 */
	public HrManager(String name, int employeeID) {
		super(name, employeeID);
	}
	
	/**
	 * 
	 * @param name
	 * @param employeeID
	 * @param driversLicense
	 */
	public boolean addDriver(String name, int employeeID, String driversLicense) {
		IDriver driver = new Driver(name, employeeID, driversLicense);
		return a.createDriver(driver);
		
	}
	
	public void removeDriver(int employeeID) {
		a.deleteDriver(employeeID);
	}
	
	public void addDrivingShift(int shiftID, String startTime, String finishTime, Driver shiftDriver) {
		DrivingShift shift = new DrivingShift(shiftID, startTime, finishTime, shiftDriver);
	}
	
	public void removeDrivingShift(int shiftID) {
		
	}

}
