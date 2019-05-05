package application;
	

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.persistence.EntityManager;
import controller.Controller;
import controller.IController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.*;
import util.HibernateUtil;
import util.Strings;
import view.*;


public class Main extends Application implements IView {
	
	public final static int DRIVER_VIEW = 1, HR_VIEW = 2, SUPERIOR_VIEW = 3;
	private static int LOGGED_IN_ID = 0;
	private BorderPane root;
	private ViewModule landing;
	private IController controller;
	private ViewModule superiorView;
	private ViewModule hrView;
	private HBox bottomBox;
	private EntityManager entityManager;
	private boolean startUpFinish = false;
	private Strings strings;
	
	@Override
	public void init() {
		
		new Thread(() -> {
			System.out.println("entity manager ----------");
			entityManager = HibernateUtil.getEntityManager();
			System.out.println("active? " + entityManager.getTransaction().isActive());
			System.out.println("finished creating entity manager");
			
			this.controller = new Controller(this);
			
			createTestDrivers();
			createTestShifts();
			createTestVehicles();
			createTestHRManagers();
			createSuperiors();
			createTestCargo();
			createTestClients();
			
			startUpFinish = true;
		}).start();
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//get an instance of Strings
			this.strings = Strings.getInstance();
			//set default language as finnish
			strings.changeBundle(new Locale("fi", "FI"));
			
			//create root BorderPane
			this.root = new BorderPane();
			
			//create a "loading spinner"
			root.setCenter(createLoadingSpinner());
			
			Scene scene = new Scene(root,1024,768);
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
						Thread.currentThread().interrupt();
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
	
	private ProgressIndicator createLoadingSpinner() {
		ProgressIndicator p = new ProgressIndicator();
		p.setProgress(-1);
		p.setVisible(true);
		p.setMaxSize(50, 50);
		return p;
	}
	
	@Override
	public void createViews() {
		
		//create and set landing view
		this.landing = new LandingView(this.controller, this);
		root.setCenter(landing.getView());
		
		//create HR view
		this.hrView = new HRView(controller);
		
		//logout button
		Button logout = createLogoutButton();	
		bottomBox = new HBox();
		bottomBox.setPadding(new Insets(0, 50, 50, 50));
		bottomBox.getChildren().addAll(logout);
		
		//create SuperiorView
		this.superiorView = new SuperiorViewController(controller);
		
	}
	
	private Button createLogoutButton() {
		Button logout = new Button(strings.getString("logout_text"));
		logout.setOnAction(e -> {
			this.root.setCenter(landing.getView());
			Main.setLoggedInId(0);
			this.root.setTop(null);
			root.setBottom(null);
		});
		return logout;
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
			DrivingShift shift = new DrivingShift(new Client("Reiskan paja"), cargo, LocalDate.now());
			cargo.setShift(shift);
			if(i == 1) {
				cargo.setHazardous(true);
			} else if(i == 2) {
				shift.getClient().setName("Metropolia");
			} else if(i == 3) {
				shift.getClient().setName("Shell");
			} else if(i == 0) {
				shift.getClient().setName("Iso Omena");
			}
			shifts.add(shift);
		}
		shifts.forEach(e -> {
			this.controller.createDrivingShift(e);
		});
		
		System.out.println("finished creating shifts");

		return shifts;
	}
	
	private Collection<Cargo> createTestCargo(){
		Collection<Cargo> cargo = new ArrayList<>();
		for(int i = 0; i < 7; i++) {
			if(i < 3) {
				cargo.add(new Cargo(i+1500, true));
			} else {
				cargo.add(new Cargo(i+1500, false));
			}
			
		}
		cargo.forEach(controller::createCargo);
		return cargo;
	}
	
	private Collection<Client> createTestClients() {
		Collection<Client> clients = new ArrayList<>();
		for(int i = 0; i < 2; i++) {
			Client client = new Client("Sello");
			clients.add(client);
			if(i == 0) {
				client.setName("Rovio");
			}
		}
		clients.forEach(controller::createClient);
		return clients;
	}

	/**
	 * Change between Driver view and HR View
	 */
	@Override
	public void changeView(int view) {
		root.setBottom(bottomBox);
		if(view == Main.DRIVER_VIEW) {
			//creates a new DriverView every time to handle language changes
			this.root.setCenter(new DriverView(controller).getView());
		} else if(view == Main.HR_VIEW) {
			((HRView)hrView).updateReports();
			this.root.setCenter(this.hrView.getView());
		} else if(view == Main.SUPERIOR_VIEW) {
			this.root.setCenter(this.superiorView.getView());
		}
	}


	@Override
	public void setUndoMessage(BorderPane root) {
		this.root.setBottom(root);
	}


	/**
	 * Set logout and language buttons back to the root
	 */
	@Override
	public void resetRootBottom() {
		root.setBottom(bottomBox);
	}
	
	public static void setLoggedInId(int id) {
		Main.LOGGED_IN_ID = id;
	}
	
	public static int loggedInId() {
		return Main.LOGGED_IN_ID;
	}

}
