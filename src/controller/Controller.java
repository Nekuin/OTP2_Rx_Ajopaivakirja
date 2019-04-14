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
	 * @param view view that the controller is going to use
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
		if(!driver.getCanDriveHazardous()) {
			return readAllDrivingShifts().stream()
					.filter(cargoList -> cargoList.getCargo()
					.stream().anyMatch(Cargo::isHazardous)).collect(Collectors.toList());
		} else {
			return readAllDrivingShifts();
		}
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
	public List<Client> readAllClients() {
		return this.clientAO.getAll();
	}

	@Override
	public void createCargo(Cargo cargo) {
		this.cargoAO.create(cargo);
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
	
	


}
