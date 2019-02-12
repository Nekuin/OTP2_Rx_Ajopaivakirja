package application;
	
import org.hibernate.SessionFactory;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.*;
import util.HibernateUtil;
import view.*;


public class Main extends Application {
	
	@Override
	public void init() {
		AjoAccessObject a = new AjoAccessObject();
		IDriver driver = new Driver("nimi", 1, "AB");
		a.createDriver((Driver)driver);
		
		/*SessionFactory factory = (SessionFactory) HibernateUtil.getSessionFactory().openSession();
		System.out.println("Session 1: " + factory.hashCode());
		System.out.println("Session 2: " + factory.hashCode());*/
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//asd dev
			BorderPane root = new BorderPane();
			NavigationBar navbar = new NavigationBar(root);
			
			DriverView dv = new DriverView();
			root.setCenter(dv.getDriverView());
			root.setTop(navbar.getNavigationBar());
			
			
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
}
