package model;

import javax.persistence.*;

@Entity
public class Driver extends Employee {

	@Column(name="driverslicense")
	private String driversLicense;
	
	@Column(name="drivenhours")
	private double drivenHours;
	
	@Column(name="drivendistance")
	private double drivenDistance;
	
	@Column(name="drivencargo")
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
	
	public boolean driveShift(DrivingShift shift) {
		shift.setShiftDriven();
		return shift.isShiftDriven();
	}
	
}
