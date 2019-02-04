package model;

public class HrManager extends Employee {

	public HrManager(String name, int employeeID) {
		super(name, employeeID);
	}
	
	public void addDriver(String name, int employeeID, String driversLicense) {
		Driver driver = new Driver(name, employeeID, driversLicense);
		
	}
	
	public void removeDriver(int employeeID) {
		
	}
	
	public void addDrivingShift() {
		
	}
	
	public void removeDrivingShift(int shiftID) {
		
	}

}
