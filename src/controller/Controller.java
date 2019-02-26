package controller;

import java.util.List;
import java.util.stream.Collectors;

import model.DriverAccessObject;
import model.DrivingShiftAO;
import model.IDriver;
import model.IDrivingShift;
import model.IHrManager;
import view.IView;

public class Controller implements IController{
	
	private IView view;
	private DriverAccessObject driverAccessObject;
	private DrivingShiftAO drivingShiftAO;
	
	public Controller(IView view) {
		this.view = view;
		this.driverAccessObject = new DriverAccessObject();
		this.drivingShiftAO = new DrivingShiftAO();
	}
	
	@Override
	public void assignShift(IDriver driver, IDrivingShift shift) {
		if(shift.getShiftTaken()) {
			System.out.println("shift " + shift.getShiftID() + " was taken");
			return;
		}
		driver.addDrivingShift(shift);
		shift.setShiftDriver(driver);
		shift.setShiftTaken(true);
		this.driverAccessObject.updateDriver(driver);
		this.drivingShiftAO.updateDrivingShift(shift);
		this.view.setDriverData(this.readAllDrivers());
		this.view.setShiftData(this.readAllDrivingShifts());
	}

	@Override
	public void createDriver(IDriver driver) {
		this.driverAccessObject.createDriver(driver);
	}

	@Override
	public void createDrivingShift(IDrivingShift shift) {
		this.drivingShiftAO.createDrivingShift(shift);
	}

	@Override
	public void updateDriver(IDriver driver) {
		this.driverAccessObject.updateDriver(driver);
	}

	@Override
	public void updateDrivingShift(IDrivingShift shift) {
		this.drivingShiftAO.updateDrivingShift(shift);
	}

	@Override
	public List<IDriver> readAllDrivers() {
		List<IDriver> drivers = this.driverAccessObject.readDriver().stream().collect(Collectors.toList());
		return drivers;
	}

	@Override
	public List<IDrivingShift> readAllDrivingShifts() {
		List<IDrivingShift> shifts = this.drivingShiftAO.readDrivingShift().stream().collect(Collectors.toList());
		return shifts;
	}

	@Override
	public void createHrManager(IHrManager manager) {
		
	}

}
