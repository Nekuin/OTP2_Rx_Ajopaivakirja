package application;
	

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import controller.Controller;
import controller.IController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.*;
import util.HibernateUtil;
import view.*;


public class Main extends Application implements IView {
	
	public static int DRIVER_VIEW = 1, HR_VIEW = 2;
	public static int LOGGED_IN_ID = 0;
	private BorderPane root;
	private DriverView dv;
	private HRView hr;
	private LandingView landing;
	private DriverReserveView driverRes;
	private IController controller;
	private EntityManager entityManager;
	
	
	
	@Override
	public void init() {
		System.out.println("entity manager ----------");
		entityManager = HibernateUtil.getEntityManager();
		System.out.println("active? " + entityManager.getTransaction().isActive());
		System.out.println("finished creating entity manager");
		
		this.controller = new Controller(this);
		
		
		Collection<Driver> ds = this.createTestDrivers();
		Collection<DrivingShift> shifts = createTestShifts();
		Collection<Vehicle> vehicles = createTestVehicles();
		Collection<HrManager> mg = createTestHRManagers();
		Collection<Superior> superiors = createSuperiors();
		
		List<Driver> drivers = this.controller.readAllDrivers();
		System.out.println("queried drivers: ------");
		drivers.forEach(System.out::println);
		System.out.println("---------");
		List<HrManager> managers = this.controller.readAllHrManagers();
		System.out.println("queried hr managers: ---------");
		managers.forEach(System.out::println);
		System.out.println("----------");
		
		ds.forEach(e -> {
			e.addDrivenCargo(50);
			this.controller.updateDriver(e);
		});
		
		
		
		
		Driver d1 = this.controller.readDriver(1);
		d1.setCanDriveHazardous(false);
		System.out.println("driver 1: " + d1);
		//System.out.println("shifts d1 can drive: " + ((Controller) controller).readGoodDrivingShifts(d1));
		
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//create root BorderPane
			this.root = new BorderPane();
			
			
			//create and set driver view
			this.dv = new DriverView(this.controller);
			//root.setCenter(dv.getDriverView());
			
			//create and set landing view
			this.landing = new LandingView(this.controller);
			root.setCenter(landing.getLandingView());
			
			/*
			//create and set Navigation bar
			NavigationBar navbar = new NavigationBar(this);
			root.setTop(navbar.getNavigationBar());
			*/
			
			
			//create hr view
			this.hr = new HRView(this.controller);
			
			
			//for testing
			
			driverRes = new DriverReserveView(this.controller);
			
			
			//this.setDriverData(getTestDrivers());
			Button driverResButton = new Button("Driver res");
			Button driverViewButton = new Button("Driver view");
			driverResButton.getStyleClass().add("navButton");
			driverViewButton.getStyleClass().add("navButton");
			
			NavBar nav = new NavBar(this, new Button[]{driverResButton, driverViewButton});
			driverResButton.setOnAction(e -> {
				root.setCenter(driverRes.getDriverReserveView());
				driverRes.setNavBar(nav);
			});
			driverViewButton.setOnAction(e -> {
				root.setCenter(this.dv.getDriverView());
				dv.setNavBar(nav);
			});
			
			
			driverRes.setNavBar(nav);
			
			
			
			
			//this.setShiftData(this.controller.readAllDrivingShifts());
			
			Scene scene = new Scene(root,720,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setOnCloseRequest(e -> {
				Platform.exit();
				System.exit(0);
			});
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private Collection<Vehicle> createTestVehicles(){
		Collection<Vehicle> vehicles = new ArrayList<>();
		
		Vehicle testCar1 = new Vehicle("ROK-666", 1500, 10, "Sprintteri", "Mersu", false);
		Vehicle testCar2 = new Vehicle("TTT-777", 1600, 100, "Pantteri", "Mersu", false);
		
		vehicles.add(testCar1);
		vehicles.add(testCar2);
		
		if(entityManager == null) {
			System.out.println("voi mätä");
			System.exit(-1);
		}
		vehicles.forEach(e -> {
			this.controller.createVehicle(e);
		});
		System.out.println("All the vehicles onboard");
		return vehicles;
	}
	
	private Collection<Driver> createTestDrivers(){
		Collection<Driver> drivers = new ArrayList<>();
		
		Driver d1 = new Driver("Eka", "A");
		Driver d2 = new Driver("Toka", "B");
		Driver d3 = new Driver("Kolmas", "AB");
		
		drivers.add(d1);
		drivers.add(d2);
		drivers.add(d3);
		
		
		if(entityManager == null) {
			System.out.println("uh oh");
			System.exit(-1);
		}
		drivers.forEach(e -> {
			this.controller.createDriver(e);
			//entityManager.persist(e);
		});
		System.out.println("finished creating drivers");
		return drivers;
		
	}
	
	private Collection<HrManager> createTestHRManagers(){
		Collection<HrManager> managers = new ArrayList<>();
		HrManager m1 = new HrManager("Masa Manageeri");
		managers.add(m1);
		managers.forEach(e -> {
			this.controller.createHrManager(e);
		});
		System.out.println("finished creating managers");
		return managers;
	}
	
	private Collection<Superior> createSuperiors(){
		Collection<Superior> superiors = new ArrayList<>();
		Superior s1 = new Superior("Esimees");
		superiors.add(s1);
		superiors.forEach(s -> {
			this.controller.createSuperior(s);
		});
		System.out.println("finished creating superiors");
		return superiors;
	}
	private Collection<DrivingShift> createTestShifts(){
		Collection<DrivingShift> shifts = new ArrayList<>();
		for(int i = 0; i < 4; i++) {
			Cargo cargo = new Cargo(i, false);
			DrivingShift shift = new DrivingShift(new Client("client"), cargo);
			cargo.setShift(shift);
			if(i == 1) {
				cargo.setHazardous(true);
			}
			shifts.add(shift);
		}
		shifts.forEach(e -> {
			this.controller.createDrivingShift(e);
		});
		
		System.out.println("finished creating shifts");

		return shifts;
	}

	/**
	 * Update user interface with a Collection of new drivers
	 */
	@Override
	public void setDriverData(Collection<Driver> drivers) {
		//this.dv.updateDrivers(drivers);
	}
	
	@Override
	public void setShiftData(Collection<DrivingShift> shifts) {
		this.driverRes.updateShiftList(shifts);
	}

	/**
	 * Change between Driver view and HR View
	 */
	@Override
	public void changeView(int view) {
		if(view == Main.DRIVER_VIEW) {
			this.dv.updateDriver();
			this.driverRes.updateShiftList(this.controller.readGoodDrivingShifts(this.controller.readDriver(Main.LOGGED_IN_ID)));
			this.root.setCenter(this.driverRes.getDriverReserveView());
		} else if(view == Main.HR_VIEW) {
			this.hr.updateDrivers(this.controller.readAllDrivers());
			this.root.setCenter(this.hr.getHRView());
		}
	}
}
