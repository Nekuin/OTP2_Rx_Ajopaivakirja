package model;

import javax.persistence.*;

@Entity
public class Driver extends Employee implements IDriver{

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
	
	@Override
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
	
	@Override
	public boolean driveShift(IDrivingShift shift) {
		shift.setShiftDriven();
		return shift.isShiftDriven();
	}

	@Override
	public void addDrivenHours(double drivenHours) {
		this.drivenHours += drivenHours;
		
	}

	@Override
	public void addDrivenDistance(double drivenDistance) {
		this.drivenDistance += drivenDistance;
		
	}

	@Override
	public void addDrivenCargo(double drivenCargo) {
		this.drivenCargo += drivenCargo;
		
	}

	
	public boolean driveShift(IDrivingShift shift) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
