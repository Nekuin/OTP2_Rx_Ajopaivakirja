package view;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import application.Main;
import controller.IController;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Driver;
import model.HrManager;
import model.Superior;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * A module which has the "login" functions
 * @author Nekuin
 *
 */
public class LandingView implements ViewModule {
	
	private IController controller;
	private BorderPane bpane;
	
	/**
	 * Constructor which takes a Controller as a parameter
	 * @param controller instance of Controller
	 */
	public LandingView(IController controller) {
		this.controller = controller;
		this.bpane = new BorderPane();
		
		GridPane pane = loginPane();
		pane.setId("loginPane");
		this.bpane.setCenter(pane);
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
		idField.setId("id-field");
		
		
		Button loginButton = new Button(Main.b.getString("login_text"));
		loginButton.setOnAction(e -> {
			int id = Integer.parseInt(idField.getText());
			idField.setText("");
			login(id);
		});
		loginButton.setId("login-button");
		
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
		
		//check if the id belongs to a Superior
		List<Superior> superiors = this.controller.readAllSuperiors();
		superiors.forEach(e -> {
			if(e.getEmployeeID() == id) {
				System.out.println("logged in as a superior");
				Main.LOGGED_IN_ID = id;
				this.controller.changeView(Main.SUPERIOR_VIEW);
				return;
			}
		});
		
	}
	

	@Override
	public void setNavBar(NavBar navBar) {
	}

	/**
	 * Get the whole LandingView module
	 * @return
	 */
	@Override
	public BorderPane getView() {
		return this.bpane;
	}
	
}
