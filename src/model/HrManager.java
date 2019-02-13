package model;

public class HrManager extends Employee {

	private DriverAccessObject a;
	/**
	 * Constructor for the hr manager
	 * @param name name of the hr manager
	 * @param employeeID id of the hr manager
	 */
	public HrManager(String name) {
		super(name);
	}
	
	
	/**
	 * Empty constructor for HrManager
	 */
	public HrManager() {
		
	}
	
	public void setAjoAccessObject(DriverAccessObject a) {
		this.a = a;
	}
	
	/**
	 * Add driver to the database
	 * @param name name of the driver
	 * @param employeeID id of the driver
	 * @param driversLicense drivers license of the driver
	 */
	public boolean addDriver(String name, String driversLicense) {
		IDriver driver = new Driver(name,driversLicense);
		return a.createDriver(driver);
		
	}
	
	public void removeDriver(int employeeID) {
		a.deleteDriver(employeeID);
	}
	
	public void addDrivingShift(int shiftID, String startTime, String finishTime, Driver shiftDriver) {
		DrivingShift shift = new DrivingShift(startTime, finishTime, shiftDriver);
	}
	
	public void removeDrivingShift(int shiftID) {
		
	}

}
