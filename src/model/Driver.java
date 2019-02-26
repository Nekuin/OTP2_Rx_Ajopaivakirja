package model;

import java.util.ArrayList;
import java.util.List;

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
	
	public String getDriversLicense() {
		return driversLicense;
	}

	public void setDriversLicense(String driversLicense) {
		this.driversLicense = driversLicense;
	}

	public double getDrivenHours() {
		return drivenHours;
	}

	public void addDrivenHours(double drivenHours) {
		this.drivenHours += drivenHours;
		
	}

	public void setDrivenHours(double drivenHours) {
		this.drivenHours = drivenHours;
	}

	public double getDrivenDistance() {
		return drivenDistance;
	}

	public void addDrivenDistance(double drivenDistance) {
		this.drivenDistance += drivenDistance;
		
	}

	public void setDrivenDistance(double drivenDistance) {
		this.drivenDistance = drivenDistance;
	}

	public double getDrivenCargo() {
		return drivenCargo;
	}

	public void addDrivenCargo(double drivenCargo) {
		this.drivenCargo += drivenCargo;
		
	}

	public void setDrivenCargo(double drivenCargo) {
		this.drivenCargo = drivenCargo;
	}

	public boolean driveShift(DrivingShift shift) {
		shift.setShiftDriven(true);
		return shift.isShiftDriven();
	}

	public String toString() {
		return super.toString() + ", license: " + this.driversLicense + " shifts: " + this.getShift();
	}

	public void addDrivingShift(DrivingShift drivingShift) {
		//shifts.add(drivingShift);
		this.shift.add(drivingShift);
		System.out.println("added shift: " + drivingShift);
		//this.shiftID = drivingShift.getShiftID();
	}

	public List<DrivingShift> getShift() {
		return this.shift;
	}

	
	
}
