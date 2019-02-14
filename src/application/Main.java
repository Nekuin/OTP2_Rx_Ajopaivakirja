package application;
	

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
	private IController controller;
	
	@Override
	public void init() {
		this.controller = new Controller(this);
		new Thread(() -> {
			/*
			this.a = new DriverAccessObject();
			this.shiftAO = new DrivingShiftAO();
			*/
			//get test drivers and shifts, and make a List copy of them
			Collection<IDriver> drivers = getTestDrivers();
			Collection<IDrivingShift> drivingShifts = getTestShifts();
			List<IDriver> d = drivers.stream().collect(Collectors.toList());
			List<IDrivingShift> s = drivingShifts.stream().collect(Collectors.toList());
			
			System.out.println("drivers and shifts---------------");
			d.forEach(System.out::println);
			s.forEach(System.out::println);
			System.out.println("------------");
			/*
			IDriver eka = d.get(0);
			eka.addDrivingShift(s.get(0));
			eka.addDrivingShift(s.get(1));
			IDriver toka = d.get(1);
			toka.addDrivingShift(s.get(2));
			this.a.updateDriver(eka);
			this.a.updateDriver(toka);
			System.out.println("with shifts:");
			d.forEach(System.out::println);
			
			//read and print drivers from database
			IDriver driver = this.a.readDriver(1);
			IDrivingShift shift = this.shiftAO.readDrivingShift(driver.getShift().get(0).getShiftID());
			//prints Employee: Eka, id: 1, license: A shifts: [Shift id: 1 null null, Shift id: 2 null null] their shift: Shift id: 1 null null
			//need to create ManyToOne stuff for client and cargo
			System.out.println(driver + " their shift: " + shift);
			*/
			setDriverData(drivers);
			setShiftData(drivingShifts);
			
		}).start();
		
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//create root BorderPane
			this.root = new BorderPane();
			
			//create and set driver view
			this.dv = new DriverView(this.controller);
			root.setCenter(dv.getDriverView());
			
			
			
			
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
		drivers.forEach(e -> {
			this.controller.createDriver(e);
		});
		System.out.println("finished creating drivers");
		return drivers;
		
	}
	
	
	private Collection<IDrivingShift> getTestShifts(){
		Collection<IDrivingShift> shifts = new ArrayList<>();
		for(int i = 0; i < 4; i++) {
			IDrivingShift shift = new DrivingShift(new Client("client"), new Cargo(i, false));
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
