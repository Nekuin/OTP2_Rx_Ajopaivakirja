package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import application.Main;
import model.Driver;
import model.DriverAccessObject;
import model.DrivingShiftAO;
import model.HrAccessObject;
import model.HrManager;
import model.ICargo;
import model.IDriver;
import model.IDrivingShift;
import model.IHrManager;
import view.IView;

public class Controller implements IController{
	
	private IView view;
	private DriverAccessObject driverAccessObject;
	private DrivingShiftAO drivingShiftAO;
	private HrAccessObject hrAO;
	
	public Controller(IView view) {
		this.view = view;
		/*
		this.driverAccessObject = new DriverAccessObject();
		this.drivingShiftAO = new DrivingShiftAO();
		this.hrAO = new HrAccessObject();
		*/
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
	
	public List<IDrivingShift> readGoodDrivingShifts(IDriver driver) {
		List<IDrivingShift> shifts = this.drivingShiftAO.readDrivingShift().stream().collect(Collectors.toList());
		List<IDrivingShift> goodShifts = new ArrayList<IDrivingShift>();
		boolean drivable;
		
		if(driver.getCanDriveHazardous() == false) {
			
			for(IDrivingShift shift : shifts) {
				drivable = true;
				
				for(ICargo c : shift.getCargo()) {
					
					if(c .isHazardous() == true) {
						drivable = false;
					}
				}
				
				if(drivable == true) {
					goodShifts.add(shift);
				}
			}
		}
		
		return goodShifts;
	}
	

	@Override
	public void createHrManager(IHrManager manager) {
		this.hrAO.createHrManager(manager);
	}

	@Override
	public List<IHrManager> readAllHrManagers() {
		List<IHrManager> managers = this.hrAO.readHrManager().stream().collect(Collectors.toList());
		return managers;
	}

	@Override
	public List<IDriver> queryDrivers() {
		EntityManager em = Main.emf.createEntityManager();
		em.getTransaction().begin();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Driver> criteria = builder.createQuery(model.Driver.class);
		criteria.from(model.Driver.class);
		List<Driver> drivers = em.createQuery(criteria).getResultList();
		return drivers.stream().collect(Collectors.toList());
	}

}
