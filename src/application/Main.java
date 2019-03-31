package application;
	

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import controller.Controller;
import controller.IController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;
import util.HibernateUtil;
import view.*;


public class Main extends Application implements IView {
	
	public static int DRIVER_VIEW = 1, HR_VIEW = 2, SUPERIOR_VIEW = 3;
	public static int LOGGED_IN_ID = 0;
	private BorderPane root;
	private DriverView dv;
	private HRView hr;
	private ViewModule landing;
	private ViewModule driverRes;
	private ViewModule personalShift;
	private ViewModule supView;
	private ViewModule supEmpView;
	private IController controller;
	private EntityManager entityManager;
	private boolean startUpFinish = false;
	public static ResourceBundle b;
	
	@Override
	public void init() {
		
		new Thread(() -> {
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
			startUpFinish = true;
		}).start();
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//set default language as finnish
			Locale.setDefault(new Locale("fi", "FI"));
			//load resources
			Main.b = ResourceBundle.getBundle("Strings");
			
			//create root BorderPane
			this.root = new BorderPane();
			
			root.setCenter(new Text("Loading..."));
			
			Scene scene = new Scene(root,720,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setOnCloseRequest(e -> {
				Platform.exit();
				System.exit(0);
			});
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
			new Thread(() -> {
				//wait for Hibernate to start up
				while(!startUpFinish)
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				
				//createViews must be called on UI thread!!
				Platform.runLater(() -> {
					createViews();
				});
				
			}).start();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createViews() {
		
		//create and set driver view
		this.dv = new DriverView(this.controller);
		
		//create DriverReserveView
		driverRes = new DriverReserveView(this.controller);
		
		//create PersonalShiftView
		personalShift = new PersonalShiftView(this.controller);
		
		
		//navigation for Driver
		Button driverResButton = new Button(Main.b.getString("driver_reserve_nav_text"));
		Button driverViewButton = new Button(Main.b.getString("driver_report_nav_text"));
		
		NavBar nav = new NavBar(this, driverResButton, driverViewButton);
		driverResButton.setOnAction(e -> {
			root.setCenter(driverRes.getView());
			driverRes.setNavBar(nav);
		});
		driverViewButton.setOnAction(e -> {
			root.setCenter(this.personalShift.getView());
			personalShift.setNavBar(nav);
		});
		
		
		driverRes.setNavBar(nav);
		
		//create and set landing view
		this.landing = new LandingView(this.controller);
		root.setCenter(landing.getView());
		
		//create hr view
		this.hr = new HRView(this.controller);
		
		//logout button
		Button logout = new Button(Main.b.getString("logout_text"));
		logout.setOnAction(e -> {
			this.root.setCenter(landing.getView());
			Main.LOGGED_IN_ID = 0;
		});
		
		//create a button to change the language to Finnish
		Button fi = new Button("FI");
		fi.setOnAction(e -> {
			Locale.setDefault(new Locale("fi", "FI"));
			Main.b = ResourceBundle.getBundle("Strings");
			createViews();
		});
		
		//create a button to change the language to English
		Button us = new Button("US");
		us.setOnAction(e -> {
			Locale.setDefault(new Locale("en", "US"));
			Main.b = ResourceBundle.getBundle("Strings");
			createViews();
		});
		
		HBox hbox = new HBox();
		hbox.getChildren().addAll(logout, fi, us);
		this.root.setBottom(hbox);
		
		//create SuperiorView
		this.supView = new SuperiorView(this.controller);
		
		//create SuperiorEmployeeView
		this.supEmpView = new SuperiorEmployeeView(this.controller);
		
		//create Buttons for Superior navBar
		Button supViewButton = new Button("Superior Vehicle");
		Button supEmpViewButton = new Button("Superior Employees");
		
		//create navBar for Superior
		NavBar supNav = new NavBar(this, supViewButton, supEmpViewButton);
		supEmpViewButton.setOnAction(e -> {
			root.setCenter(supEmpView.getView());
			supEmpView.setNavBar(supNav);
		});
		
		supViewButton.setOnAction(e -> {
			root.setCenter(supView.getView());
			supView.setNavBar(supNav);
		});
		
		//set navBar for SuperiorView
		supView.setNavBar(supNav);
		
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
		
		Driver d1 = new Driver("Kerkko Kuski", "A");
		Driver d2 = new Driver("Reiska Rekkamies", "B");
		Driver d3 = new Driver("Kari Kaahari", "AB");
		
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
		Superior s1 = new Superior("Esa Esimees");
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
		((DriverReserveView)driverRes).updateShiftList(shifts);
	}

	/**
	 * Change between Driver view and HR View
	 */
	@Override
	public void changeView(int view) {
		if(view == Main.DRIVER_VIEW) {
			this.dv.updateDriver();
			((DriverReserveView)this.driverRes).updateShiftList(this.controller.readGoodDrivingShifts(this.controller.readDriver(Main.LOGGED_IN_ID)));
			this.root.setCenter(this.driverRes.getView());
		} else if(view == Main.HR_VIEW) {
			this.hr.updateDrivers(this.controller.readAllDrivers());
			this.root.setCenter(this.hr.getView());
		} else if(view == Main.SUPERIOR_VIEW) {
			this.root.setCenter(this.supView.getView());
		}
	}
}
