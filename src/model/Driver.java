package model;

public class Driver extends Employee {

	private String driversLicense;
	private double drivenHours;
	private double drivenDistance;
	private double drivenCargo;
	
	public Driver(String name, int employeeID, String driversLicense) {
		super(name, employeeID);
		this.driversLicense = driversLicense;
	}
	
	public String getDriversLicense() {
		return driversLicense;
	}

	public void setDriversLicense(String driversLicense) {
		this.driversLicense = driversLicense;
	}

	public double getDrivenHours() {
		return drivenHours;
	}

	public void setDrivenHours(double drivenHours) {
		this.drivenHours = drivenHours;
	}

	public double getDrivenDistance() {
		return drivenDistance;
	}

	public void setDrivenDistance(double drivenDistance) {
		this.drivenDistance = drivenDistance;
	}

	public double getDrivenCargo() {
		return drivenCargo;
	}

	public void setDrivenCargo(double drivenCargo) {
		this.drivenCargo = drivenCargo;
	}
	
	public boolean driveShift(int shiftID) {
		return false;
	}
	
}
