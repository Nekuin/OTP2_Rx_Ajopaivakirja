package model;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;


/**
 * 
 * @author tuoma
 *	Class for drivers
 */
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
	
	@Column(name="shiftsReserved")
	private int shiftsReserved;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private List<DrivingShift> shifts;
	
	/**
	 * Constructor for the driver
	 * @param name name of the driver
	 * @param driversLicense license of the driver
	 * @param canDriveHazardous indicates whether can or can't drive hazardous cargo
	 */
	public Driver(String name, String driversLicense, boolean canDriveHazardous) {
		super(name);
		this.driversLicense = driversLicense; 
		this.shifts = new ArrayList<>();
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
		this.shifts = new ArrayList<>();
		this.canDriveHazardous = false;
	}
	
	/**
	 * Empty constructor for the driver
	 */
	public Driver() {
		super();
	}
	
	/**
	 * Method that returns the drivers ability to drive hazardous cargo
	 * @return
	 */
	public boolean getCanDriveHazardous() {
		return canDriveHazardous;
	}
	
	/**
	 * Method for setting the drivers ability to drive hazardous cargo
	 * @param canDriveHazardous boolean that indicates if the driver can drive dangerous cargo
	 */
	public void setCanDriveHazardous(boolean canDriveHazardous) {
		this.canDriveHazardous = canDriveHazardous;
	}
	
	
	/**
	 * Method that returns drivers license of the driver
	 * @return
	 */
	public String getDriversLicense() {
		return driversLicense;
	}

	
	/**
	 * Method for setting the drivers license
	 * @param driversLicense license of the driver
	 */
	public void setDriversLicense(String driversLicense) {
		this.driversLicense = driversLicense;
	}

	
	/**
	 * Method for getting driven hours of the driver
	 * @return
	 */
	public double getDrivenHours() {
		return drivenHours;
	}

	
	/**
	 * Method for adding hours to the driven hours of the driver
	 * @param drivenHours driven hours of the driver
	 */
	public void addDrivenHours(double drivenHours) {
		this.drivenHours += drivenHours;
		
	}

	
	/**
	 * Method for setting the driven hours of the driver
	 * @param drivenHours driven hours of the driver
	 */
	public void setDrivenHours(double drivenHours) {
		this.drivenHours = drivenHours;
	}

	
	/**
	 * Method for getting the driven distance of the driver
	 * @return
	 */
	public double getDrivenDistance() {
		return drivenDistance;
	}

	
	/**
	 * Method for adding distance to the drivers driven distance 
	 * @param drivenDistance distance the driver has driven
	 */
	public void addDrivenDistance(double drivenDistance) {
		this.drivenDistance += drivenDistance;
		
	}

	
	/**
	 * Method for setting the driven distance
	 * @param drivenDistance distance the driver has driven
	 */
	public void setDrivenDistance(double drivenDistance) {
		this.drivenDistance = drivenDistance;
	}

	
	/**
	 * Method that returns the amount of cargo the driver has driven
	 * @return
	 */
	public double getDrivenCargo() {
		return drivenCargo;
	}

	
	/**
	 * Method for increasing the amount of cargo the driver has driven
	 * @param drivenCargo amount of cargo the driver has driven
	 */
	public void addDrivenCargo(double drivenCargo) {
		this.drivenCargo += drivenCargo;
		
	}

	/**
	 * Method for setting the amount of driven cargo
	 * @param drivenCargo
	 */
	public void setDrivenCargo(double drivenCargo) {
		this.drivenCargo = drivenCargo;
	}

	
	/**
	 * Method that tries to set the drivers shift to be  driven
	 * @param shift current shift of the driver
	 * @return
	 */
	public boolean driveShift(DrivingShift shift) {
		shift.setShiftDriven(true);
		return shift.isShiftDriven();
	}

	
	/**
	 * Method that returns information about the driver in string format
	 */
	@Override
	public String toString() {
		return super.toString() + ", License: " + this.driversLicense;
	}

	
	/**
	 * Method that adds a shift to the list of drivers shifts
	 * @param drivingShift shift thats going to bo added for a driver
	 */
	public void addDrivingShift(DrivingShift drivingShift) {
		//shifts.add(drivingShift);
		this.shifts.add(drivingShift);
		System.out.println("added shift: " + drivingShift);
		//this.shiftID = drivingShift.getShiftID();
	}

	
	/**
	 * Returns list of shifts the driver has
	 * @return
	 */
	public List<DrivingShift> getShifts() {
		return this.shifts;
	}
	
	public int getShiftsReserved() {
		shiftsReserved = getShifts().size();
		return shiftsReserved;
	}
	
}
