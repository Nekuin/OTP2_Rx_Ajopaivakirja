package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.*;
import util.HibernateUtil;
import view.IView;

public class Controller implements IController{
	
	private IView view;
	private DrivingShiftAO drivingShiftAO;
	private HrAccessObject hrAO;
	private IDao<Driver> driverAccessObject;
	
	public Controller(IView view) {
		this.view = view;
		this.driverAccessObject = new DriverAccessObject();
		this.drivingShiftAO = new DrivingShiftAO();
		//this.hrAO = new HrAccessObject();
		
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
		this.drivingShiftAO.updateDrivingShift(shift);
		this.view.setDriverData(this.readAllDrivers());
		this.view.setShiftData(this.readAllDrivingShifts());
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
		this.drivingShiftAO.updateDrivingShift(shift);
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
		List<DrivingShift> shifts = this.drivingShiftAO.readDrivingShift();
		return shifts;
	}
	
	public List<DrivingShift> readGoodDrivingShifts(Driver driver) {
		List<DrivingShift> shifts = this.drivingShiftAO.getAll();
		System.out.println("shifts: " + shifts);
		List<DrivingShift> goodShifts = new ArrayList<>();
		
		if(!driver.getCanDriveHazardous()) {
			goodShifts = shifts.stream().filter(cargoList -> cargoList.getCargo()
					.stream().anyMatch(e -> e.isHazardous())).collect(Collectors.toList());
			
		} else {
			return shifts;
		}
		
		return goodShifts;
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
	public void deleteDriver(Driver driver) {
		this.driverAccessObject.delete(driver);
	}


}
