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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.*;
import util.HibernateUtil;
import view.*;


public class Main extends Application implements IView {
	
	public static int DRIVER_VIEW = 1, HR_VIEW = 2;
	private BorderPane root;
	private DriverView dv;
	private HRView hr;
	private LandingView landing;
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
		
		List<Driver> drivers = this.controller.readAllDrivers();
		System.out.println("queried drivers: ------");
		drivers.forEach(System.out::println);
		System.out.println("---------");
		
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
			
			
			//create and set Navigation bar
			NavigationBar navbar = new NavigationBar(this);
			root.setTop(navbar.getNavigationBar());
			
			//create hr view
			this.hr = new HRView();
			
			
			//for testing
			//this.setDriverData(getTestDrivers());
			
			
			this.setShiftData(this.controller.readAllDrivingShifts());
			
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
		HrManager m1 = new HrManager("Manageeri");
		managers.add(m1);
		managers.forEach(e -> {
			this.controller.createHrManager(e);
		});
		System.out.println("finished creating managers");
		return managers;
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
		this.dv.updateShifts(shifts);
	}
	
	public void updateDriver(Driver driver) {
		this.dv.updateDriver(driver);
	}

	/**
	 * Change between Driver view and HR View
	 */
	@Override
	public void changeView(int view, Object employee) {
		if(view == Main.DRIVER_VIEW) {
			if(employee != null) {
				this.dv.setEmployee((Driver)employee);
			}
			this.root.setCenter(this.dv.getDriverView());
		} else if(view == Main.HR_VIEW) {
			this.root.setCenter(this.hr.getHRView());
		}
	}
}
