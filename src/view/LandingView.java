package view;

import java.util.List;

import application.Main;
import controller.IController;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Driver;
import model.HrManager;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * A module which has the "login" functions
 * @author Nekuin
 *
 */
public class LandingView {
	
	private IController controller;
	private BorderPane bpane;
	
	/**
	 * Constructor which takes a Controller as a parameter
	 * @param controller instance of Controller
	 */
	public LandingView(IController controller) {
		this.controller = controller;
		this.bpane = new BorderPane();
		
		this.bpane.setCenter(loginPane());
	}
	
	/**
	 * A module which has fields for employee ID input and a button for logging in
	 * @return GridPane
	 */
	private GridPane loginPane() {
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(30, 0, 0, 30));
		
		TextField idField = new TextField();
		idField.setPromptText("Your Employee ID");
		
		
		Button loginButton = new Button("Login");
		loginButton.setOnAction(e -> {
			int id = Integer.parseInt(idField.getText());
			idField.setText("");
			login(id);
		});
		
		idField.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ENTER) {
				this.login(Integer.parseInt(idField.getText()));
				idField.setText("");
			}
		});
		
		
		pane.add(loginButton, 1, 0);
		pane.add(idField, 0, 0);
		return pane;
	}
	
	/**
	 * Log the user in with the employeeID given as argument
	 * @param id Employee ID
	 */
	private void login(int id) {
		System.out.println("[ph] logging in as id: " + id);
		//check if the id belongs to a driver
		List<Driver> drivers = this.controller.readAllDrivers();
		drivers.forEach(e -> {
			if(e.getEmployeeID() == id) {
				System.out.println("logged in as a driver");
				Main.LOGGED_IN_ID = id;
				this.controller.changeView(Main.DRIVER_VIEW);
				return;
			}
		});
		
		//check if the id belongs to a HrManager
		List<HrManager> managers = this.controller.readAllHrManagers();
		managers.forEach(e -> {
			if(e.getEmployeeID() == id) {
				System.out.println("logged in as a manager");
				Main.LOGGED_IN_ID = id;
				this.controller.changeView(Main.HR_VIEW);
				return;
			}
		});
		
	}
	
	/**
	 * Get the whole LandingView module
	 * @return
	 */
	public BorderPane getLandingView() {
		return this.bpane;
	}
	
}
