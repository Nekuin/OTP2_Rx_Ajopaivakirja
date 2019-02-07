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
	
	
	/**
	 * Constructor of the driver
	 * @param name
	 * @param employeeID
	 * @param driversLicense
	 */
	public Driver(String name, int employeeID, String driversLicense) {
		super(name, employeeID);
		this.driversLicense = driversLicense;
	}
	
	@Override
	public String getDriversLicense() {
		return driversLicense;
	}

	@Override
	public void setDriversLicense(String driversLicense) {
		this.driversLicense = driversLicense;
	}

	@Override
	public double getDrivenHours() {
		return drivenHours;
	}
	
	@Override
	public void addDrivenHours(double drivenHours) {
		this.drivenHours += drivenHours;
		
	}

	public void setDrivenHours(double drivenHours) {
		this.drivenHours = drivenHours;
	}

	@Override
	public double getDrivenDistance() {
		return drivenDistance;
	}
	
	@Override
	public void addDrivenDistance(double drivenDistance) {
		this.drivenDistance += drivenDistance;
		
	}

	public void setDrivenDistance(double drivenDistance) {
		this.drivenDistance = drivenDistance;
	}

	@Override
	public double getDrivenCargo() {
		return drivenCargo;
	}
	
	@Override
	public void addDrivenCargo(double drivenCargo) {
		this.drivenCargo += drivenCargo;
		
	}

	public void setDrivenCargo(double drivenCargo) {
		this.drivenCargo = drivenCargo;
	}
	
	@Override
	public boolean driveShift(IDrivingShift shift) {
		shift.setShiftDriven(true);
		return shift.isShiftDriven();
	}

	

	

	
	
}
