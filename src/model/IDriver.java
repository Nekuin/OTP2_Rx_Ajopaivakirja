package model;

import java.util.List;

public interface IDriver extends IEmployee {
	
	/**
	 * Returns the type of the drivers license of the driver
	 * @return
	 */
	public String getDriversLicense();
	
	/**
	 * Sets the type of the drivers license
	 * @param driversLicense
	 */
	public void setDriversLicense(String driversLicense);
	
	/**
	 * Returns the driven hours
	 * @return
	 */
	public double getDrivenHours();
	
	/**
	 * Adds the driven hours
	 * @param drivenHours
	 */
	public void addDrivenHours(double drivenHours);
	
	/**
	 * Returns the driven distance
	 * @return
	 */
	public double getDrivenDistance();
	
	/**
	 * Adds the driven distance
	 * @param drivenDistance
	 */
	public void addDrivenDistance(double drivenDistance);
	
	/**
	 * Returns the driven cargo
	 * @return
	 */
	public double getDrivenCargo();
	
	/**
	 * Adds the driven cargo
	 * @param drivenCargo
	 */
	public void addDrivenCargo(double drivenCargo);
	
	/**
	 * Drives the shift
	 * @param shift
	 * @return
	 */
	public boolean driveShift(IDrivingShift shift);
	
	public void addDrivingShift(IDrivingShift drivingShift);
	
	//public Set<DrivingShift> getShifts();
	
	public List<DrivingShift> getShift();
	
	public boolean getCanDriveHazardous();
	
	public void setCanDriveHazardous(boolean canDriveHazardous);
}
