package controller;

import java.util.List;

import model.*;

public interface IController {

	public void assignShift(Driver driver, DrivingShift shift);
	public void createDriver(Driver driver);
	public void createDrivingShift(DrivingShift shift);
	public void updateDriver(Driver driver);
	public void updateDrivingShift(DrivingShift shift);
	public List<Driver> readAllDrivers();
	public List<DrivingShift> readAllDrivingShifts();
	public void createHrManager(HrManager manager);
	public List<HrManager> readAllHrManagers();
	public Driver readDriver(int id);
	public void deleteDriver(Driver driver);
	
}
