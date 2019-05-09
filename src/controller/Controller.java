package controller;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.layout.BorderPane;
import model.*;
import view.IView;

/**
 * 
 * @author Nekuin
 *
 */
public class Controller implements IController{
	
	private IView view;
	private Dao<DrivingShift> drivingShiftAO;
	private Dao<HrManager> hrAO;
	private Dao<Driver> driverAccessObject;
	private Dao<Cargo> cargoAO;
	private Dao<Vehicle> vehicleAO;
	private Dao<Client> clientAO;
	private Dao<Superior> superiorAO;
	private Dao<Employee> empAo;
	
	
	/**
	 * Contructor for the controller
	 * @param view instance of view
	 */
	public Controller(IView view) {
		this.view = view;
		this.drivingShiftAO = new Dao<>(model.DrivingShift.class);
		this.hrAO = new Dao<>(model.HrManager.class);
		this.driverAccessObject = new Dao<>(model.Driver.class);
		this.vehicleAO = new Dao<>(model.Vehicle.class);
		this.superiorAO = new Dao<>(model.Superior.class);
		this.empAo = new Dao<>(model.Employee.class);
		this.cargoAO = new Dao<>(model.Cargo.class);
		this.clientAO = new Dao<>(model.Client.class);
	}
	
	/**
	 * Constructor for the JUnit testing version of the controller
	 * @param view instance of View or null for testing
	 * @param test boolean indicating whether or not to use the test database on jenkins server
	 */
	public Controller(IView view, boolean test) {
		System.out.println("test controller");
		this.view = view;
		this.drivingShiftAO = new Dao<>(model.DrivingShift.class, test);
		this.hrAO = new Dao<>(model.HrManager.class, test);
		this.driverAccessObject = new Dao<>(model.Driver.class, test);
		this.vehicleAO = new Dao<>(model.Vehicle.class, test);
		this.superiorAO = new Dao<>(model.Superior.class, test);
		this.empAo = new Dao<>(model.Employee.class, test);
		this.cargoAO = new Dao<>(model.Cargo.class, test);
		this.clientAO = new Dao<>(model.Client.class, test);
	}
	
	@Override
	public void assignShift(Driver driver, DrivingShift shift) {
	
		if(shift.getShiftTaken()) {
			System.out.println("shift " + shift.getShiftID() + " was taken");
			return;
		}
		driver.addDrivingShift(shift);
		shift.setShiftDriver(driver);
		shift.setShiftTaken(true);
		this.driverAccessObject.update(driver);
		this.drivingShiftAO.update(shift);
	}

	@Override
	public void createDriver(Driver driver) {
		this.driverAccessObject.create(driver);
	}

	@Override
	public void createDrivingShift(DrivingShift shift) {
		this.drivingShiftAO.create(shift);
	}

	@Override
	public void updateDriver(Driver driver) {
		this.driverAccessObject.update(driver);
	}

	@Override
	public void updateDrivingShift(DrivingShift shift) {
		this.drivingShiftAO.update(shift);
	}
	
	@Override
	public void createVehicle(Vehicle vehicle) {
		this.vehicleAO.create(vehicle);
	}
	
	@Override
	public void updateVehicle(Vehicle vehicle) {
		this.vehicleAO.update(vehicle);
	}
	
	@Override
	public void deleteVehicle(Vehicle vehicle) {
		this.vehicleAO.delete(vehicle);
	}
	
	@Override
	public Vehicle readVehicle(int id) {
		return this.vehicleAO.get(id);
	}
	
	@Override
	public List<Vehicle> readAllVehicles(){
		List<Vehicle> vehicles = this.vehicleAO.getAll();
		return vehicles;
	}

	@Override
	public List<Driver> readAllDrivers() {
		List<Driver> drivers = this.driverAccessObject.getAll();
		return drivers;
	}
	
	@Override
	public Driver readDriver(int id) {
		return this.driverAccessObject.get(id);
	}

	@Override
	public List<DrivingShift> readAllDrivingShifts() {
		List<DrivingShift> shifts = this.drivingShiftAO.getAll();
		return shifts;
	}
	
	@Override
	public List<DrivingShift> readGoodDrivingShifts(Driver driver) {
		if(!driver.canDriveHazardous()) {		
			return readAllDrivingShifts().stream()
					.filter(shift -> shift.getShiftDriver() == null)
					.filter(cargoList -> !cargoList.getCargo().stream()
							.anyMatch(Cargo::isHazardous))
					.collect(Collectors.toList());
					
		} else {
			return readAllDrivingShifts().stream()
					.filter(shift -> shift.getShiftDriver() == null)
					.collect(Collectors.toList());
		}
	}
	
	public List<DrivingShift> readReportedShifts(){
		return readAllDrivingShifts().stream().filter(shift -> shift.getShiftReported() == true).collect(Collectors.toList());
	}
	

	@Override
	public void createHrManager(HrManager manager) {
		this.hrAO.create(manager);
	}

	@Override
	public List<HrManager> readAllHrManagers() {
		List<HrManager> managers = this.hrAO.getAll();
		return managers;
	}
	
	@Override
	public void createSuperior(Superior superior) {
		this.superiorAO.create(superior);
	}
	
	@Override
	public List<Superior> readAllSuperiors(){
		List<Superior> superiors = this.superiorAO.getAll();
		return superiors;
	}
	
	@Override
	public void deleteDriver(Driver driver) {
		this.driverAccessObject.delete(driver);
	}

	@Override
	public void changeView(int i) {
		this.view.changeView(i);
	}

	@Override
	public void updateEmployee(Employee employee) {
		this.empAo.update(employee);	
	}

	@Override
	public List<Employee> readAllEmployees() {
		List<Employee> employees = this.empAo.getAll();
		return employees;
	}
	
	@Override
	public Employee readEmployee(int id) {
		return this.empAo.get(id);
	}

	@Override
	public void deleteEmployee(Employee employee) {
		this.empAo.delete(employee);
	}

	@Override
	public void showUndoMessage(BorderPane root) {
		this.view.setUndoMessage(root);
	}

	@Override
	public void resetRootBottom() {
		view.resetRootBottom();
	}

	@Override
	public void createEmployee(Employee e) {
		this.empAo.create(e);
	}

	@Override
	public void deleteShift(DrivingShift shift) {
		this.drivingShiftAO.delete(shift);
	}

	@Override
	public List<Cargo> readAllCargo() {
		return this.cargoAO.getAll();
	}
	
	@Override
	public void createClient(Client client) {
		this.clientAO.create(client);
	}

	@Override
	public void deleteClient(Client client) {
		this.clientAO.delete(client);
	}

	@Override
	public void updateClient(Client client) {
		this.clientAO.update(client);
	}

	@Override
	public List<Client> readAllClients() {
		return this.clientAO.getAll();
	}
	
	@Override
	public Client readClient(int id) {
		return this.clientAO.get(id);
	}

	@Override
	public void createCargo(Cargo cargo) {
		this.cargoAO.create(cargo);
	}
	
	@Override
	public Cargo readCargo(int id) {
		return this.cargoAO.get(id);
	}

	@Override
	public List<Cargo> readAllUnassignedCargo() {
		return this.cargoAO.getAll().stream()
				.filter(cargo -> cargo.getShift() == null)
				.collect(Collectors.toList());
	}

	@Override
	public void updateCargo(Cargo cargo) {
		cargoAO.update(cargo);
	}

	@Override
	public void deleteCargo(Cargo cargo) {
		cargoAO.delete(cargo);
	}
	
}
