package model;

import java.util.ArrayList;
import java.util.List;

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
	
	@Column(name="candrivehazardous")
	private boolean canDriveHazardous;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private List<DrivingShift> shift;
	
	/**
	 * Constructor for the driver
	 * @param name name of the driver
	 * @param driversLicense license of the driver
	 * @param canDriveHazardous indicates whether can or can't drive hazardous cargo
	 */
	public Driver(String name, String driversLicense, boolean canDriveHazardous) {
		super(name);
		this.driversLicense = driversLicense; 
		//this.shifts = new HashSet<>();
		this.shift = new ArrayList<>();
		this.canDriveHazardous = canDriveHazardous;
	}
	
	/**
	 * Constructor for driver where canDriveHazardous is set to false by default
	 * @param name name of the driver
	 * @param driversLicense license of the driver
	 */
	public Driver(String name, String driversLicense) {
		super(name);
		this.driversLicense = driversLicense; 
		//this.shifts = new HashSet<>();
		this.shift = new ArrayList<>();
		this.canDriveHazardous = false;
	}
	
	/**
	 * Empty constructor for the driver
	 */
	public Driver() {
		super();
	}

	public boolean getCanDriveHazardous() {
		return canDriveHazardous;
	}
	
	public void setCanDriveHazardous(boolean canDriveHazardous) {
		this.canDriveHazardous = canDriveHazardous;
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

	@Override
	public String toString() {
		return super.toString() + ", license: " + this.driversLicense + " shifts: " + this.getShift();
	}

	@Override
	public void addDrivingShift(IDrivingShift drivingShift) {
		//shifts.add(drivingShift);
		this.shift.add((DrivingShift)drivingShift);
		System.out.println("added shift: " + drivingShift);
		//this.shiftID = drivingShift.getShiftID();
	}
	
	@Override
	public List<DrivingShift> getShift() {
		return this.shift;
	}

	
	
}
