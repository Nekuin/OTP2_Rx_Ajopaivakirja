package application;
	

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

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
	
	private DriverAccessObject a;
	
	@Override
	public void init() {
		new Thread(() -> {
			this.a = new DriverAccessObject();
			Collection<IDriver> drivers = this.getTestDrivers();
			drivers.forEach(e -> {
				a.createDriver(e);
			});
			Collection<IDriver> asd = a.readDriver().stream().collect(Collectors.toList());
			this.setDriverData(asd);
			this.setShiftData(getTestShifts());
			
		}).start();
		
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//create root BorderPane
			this.root = new BorderPane();
			
			//create and set driver view
			this.dv = new DriverView();
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
		return drivers;
	}
	
	private Collection<DrivingShift> getTestShifts(){
		Collection<DrivingShift> shifts = new ArrayList<>();
		Collection<IDriver> drivers = getTestDrivers();
		Iterator<IDriver> i = drivers.iterator();
		ICargo cargo = new Cargo();
		IClient client = new Client();
		
		while(i.hasNext()) {
			shifts.add(new DrivingShift(client, cargo));
		}
		
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
	public void setShiftData(Collection<DrivingShift> shifts) {
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
