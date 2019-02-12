package application;
	

import java.util.ArrayList;
import java.util.Collection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.*;
import view.*;


public class Main extends Application implements IView {
	
	private IDriverView dv;
	
	@Override
	public void init() {
		//HibernateUtil.getSessionFactory();
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//asd dev
			BorderPane root = new BorderPane();
			NavigationBar navbar = new NavigationBar(root);
			
			this.dv = new DriverView();
			root.setCenter(dv.getDriverView());
			root.setTop(navbar.getNavigationBar());
			
			//for testing
			this.setDriverData(getTestDrivers());
			
			
			Scene scene = new Scene(root,720,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
		IDriver d1 = new Driver("Eka", 1, "A");
		IDriver d2 = new Driver("Toka", 2, "B");
		IDriver d3 = new Driver("Kolmas", 3, "AB");
		drivers.add(d1);
		drivers.add(d2);
		drivers.add(d3);
		return drivers;
	}

	@Override
	public void setDriverData(Collection<IDriver> drivers) {
		this.dv.updateDrivers(drivers);
	}
}
