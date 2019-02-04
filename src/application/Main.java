package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import model.AjoAccessObject;
import view.*;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	@Override
	public void init() {
		AjoAccessObject a = new AjoAccessObject();
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
