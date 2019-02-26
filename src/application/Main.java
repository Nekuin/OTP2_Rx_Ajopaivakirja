package application;
	

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import controller.Controller;
import controller.IController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.*;
import view.*;


public class Main extends Application implements IView {
	
	public static int DRIVER_VIEW = 1, HR_VIEW = 2;
	private BorderPane root;
	private IDriverView dv;
	private IHRView hr;
	private ILandingView landing;
	private IController controller;
	
	@PersistenceContext
	EntityManager entityManager;
	
	
	@Override
	public void init() {
		System.out.println("entity manager ----------");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("otp1");
		System.out.println("create manager");
		this.entityManager = emf.createEntityManager();
		System.out.println("finished creating manager");
		this.getTestDrivers();
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
	
	
	
	private Collection<IDriver> getTestDrivers(){
		Collection<IDriver> drivers = new ArrayList<>();
		IDriver d1 = new Driver("Eka", "A");
		IDriver d2 = new Driver("Toka", "B");
		IDriver d3 = new Driver("Kolmas", "AB");
		drivers.add(d1);
		drivers.add(d2);
		drivers.add(d3);
		this.entityManager.getTransaction().begin();
		drivers.forEach(e -> {
			//this.controller.createDriver(e);
			this.entityManager.persist(e);
		});
		this.entityManager.getTransaction().commit();
		System.out.println("finished creating drivers");
		return drivers;
		
	}
	
	private Collection<IHrManager> getTestHRManagers(){
		Collection<IHrManager> managers = new ArrayList<>();
		IHrManager m1 = new HrManager("Manageeri");
		managers.add(m1);
		managers.forEach(e -> {
			this.controller.createHrManager(e);
		});
		System.out.println("finished creating managers");
		return managers;
	}
	
	
	private Collection<IDrivingShift> getTestShifts(){
		Collection<IDrivingShift> shifts = new ArrayList<>();
		for(int i = 0; i < 4; i++) {
			Cargo cargo = new Cargo(i, false);
			IDrivingShift shift = new DrivingShift(new Client("client"), cargo);
			cargo.setShift(shift);
			shifts.add(shift);
		}
		/*
		DrivingShift eka = new DrivingShift(client, cargo);
		DrivingShift toka = new DrivingShift(client, cargo);
		DrivingShift kolmas = new DrivingShift(client, cargo);
		shifts.add(eka);
		shifts.add(toka);
		shifts.add(kolmas);
		*/
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
	public void setDriverData(Collection<IDriver> drivers) {
		this.dv.updateDrivers(drivers);
	}
	
	@Override
	public void setShiftData(Collection<IDrivingShift> shifts) {
		this.dv.updateShifts(shifts);
	}

	/**
	 * Change between Driver view and HR View
	 */
	@Override
	public void changeView(int view) {
		if(view == Main.DRIVER_VIEW) {
			this.root.setCenter(this.dv.getDriverView());
		} else if(view == Main.HR_VIEW) {
			this.root.setCenter(this.hr.getHRView());
		}
	}
}
