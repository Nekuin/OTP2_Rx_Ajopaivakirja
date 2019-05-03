package controller;

import java.util.List;

import javafx.scene.layout.BorderPane;
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
	 * Returns an employee from database
	 * @param id id of the employee
	 * @return
	 */
	public Employee readEmployee(int id);
	
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
	
	/**
	 * Update an Employee in the database
	 * @param employee instance of Employee
	 */
	public void updateEmployee(Employee employee);
	
	/**
	 * Returns a list of all Employees in the database
	 * @return List of Employee objects
	 */
	public List<Employee> readAllEmployees();
	
	/**
	 * Delete an Employee from the database
	 * @param employee instance of Employee
	 */
	public void deleteEmployee(Employee employee);
	
	/**
	 * Show undo option to the user after i.e. deleting a Driving shift
	 * @param root Root of the UndoPopup object
	 */
	public void showUndoMessage(BorderPane root);
	
	/**
	 * Set Logout and language buttons back to the bottom of the root found in Main
	 */
	public void resetRootBottom();
	
	/**
	 * Create an Employee
	 * @param e instance of Employee
	 */
	public void createEmployee(Employee e);
	
	/**
	 * Delete a DrivingShift from the database
	 * @param shift instance of DrivingShift
	 */
	public void deleteShift(DrivingShift shift);
	
	/**
	 * Returns a list of all Cargo in the database
	 * @return List of Cargo objects
	 */
	public List<Cargo> readAllCargo();
	
	/**
	 * Create a client object in the database
	 * @param client
	 */
	public void createClient(Client client);
	
	/**
	 * Delete client from database
	 * @param client
	 */
	public void deleteClient(Client client);
	
	 /**
	  * Update client object in the database
	  * @param client
	  */
	public void updateClient(Client client);
	
	/**
	 * Returns a list of all Clients in the database
	 * @return List of Client objects
	 */
	public List<Client> readAllClients();
	
	/**
	 * Returns a client from database
	 * @param id
	 * @return Client
	 */
	public Client readClient(int id);
	
	/**
	 * Create a Cargo
	 * @param cargo instance of Cargo
	 */
	public void createCargo(Cargo cargo);
	
	/**
	 * Returns a list of all Cargo where shift is null
	 */
	public List<Cargo> readAllUnassignedCargo();
	
	/**
	 * Update cargo in the database
	 * @param cargo
	 */
	public void updateCargo(Cargo cargo);
	
	/**
	 * Delete cargo from database
	 * @param cargo
	 */
	public void deleteCargo(Cargo cargo);

	/**
	 * Read reported drivingshifts
	 * @return
	 */
	public List<DrivingShift> readReportedShifts();
}
