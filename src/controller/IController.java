package controller;

import java.util.List;

import model.*;

/**
 * 
 * @author tuoma
 * Interface for the controller
 */
public interface IController {

	/**
	 * Assign shift to driver
	 * @param driver driver that gets the assigned shift
	 * @param shift shift for the driver
	 */
	public void assignShift(Driver driver, DrivingShift shift);
	
	/**
	 * Creates a driver
	 * @param driver driver to be created
	 */
	public void createDriver(Driver driver);
	
	/**
	 * Creates driving shift
	 * @param shift driving shift to be created
	 */
	public void createDrivingShift(DrivingShift shift);
	
	/**
	 * Updates driver
	 * @param driver driver to be updated
	 */
	public void updateDriver(Driver driver);
	
	/**
	 * Updates a driving shift
	 * @param shift driving shift to be updated
	 */
	public void updateDrivingShift(DrivingShift shift);
	
	/**
	 * Returns a list of all drivers in database
	 * @return
	 */
	public List<Driver> readAllDrivers();
	
	/**
	 * Returns a list of all driving shifts in database
	 * @return
	 */
	public List<DrivingShift> readAllDrivingShifts();
	
	/**
	 * Creates a hr-manager
	 * @param manager Hr-manager to be created
	 */
	public void createHrManager(HrManager manager);
	
	/**
	 * Returns a list of all hr-managers in database
	 * @return
	 */
	public List<HrManager> readAllHrManagers();
	
	/**
	 * Returns a driver from database
	 * @param id id of the driver
	 * @return
	 */
	public Driver readDriver(int id);
	
	/**
	 * Deletes a driver from database
	 * @param driver driver thats going to be deleted
	 */
	public void deleteDriver(Driver driver);
	
	/**
	 * Changes the view on user interface
	 * @param i the view that is going to be used
	 */
	public void changeView(int i);
	
	/**
	 * Returns a list of driving shifts that driver can choose from based on the drivers ability to drive hazardous cargo
	 * @param driver driver who's ability to drive dangerous cargo gets checked
	 * @return
	 */
	public List<DrivingShift> readGoodDrivingShifts(Driver driver);
	
	/**
	 * Returns a list of all vehicles in the database
	 * @return
	 */
	public List<Vehicle> readAllVehicles();
	
	/**
	 * Creates a vehicle in the database
	 * @param vehicle
	 */
	public void createVehicle(Vehicle vehicle);

	/**
	 * Deletes a vehicle from database
	 * @param vehicle
	 */
	public void deleteVehicle(Vehicle vehicle);
	
	/**
	 * Creates superior
	 * @param superior
	 */
	public void createSuperior(Superior superior);

	/**
	 * Read all the superiors and make a list of them
	 * @return List of Superiors
	 */
	public List<Superior> readAllSuperiors();

	/**
	 * Update vehicle information
	 * @param vehicle
	 */
	public void updateVehicle(Vehicle vehicle);
}
