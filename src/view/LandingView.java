package view;

import java.util.List;

import application.Main;
import controller.IController;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Driver;
import model.HrManager;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class LandingView implements ILandingView{
	
	private IController controller;
	private BorderPane bpane;
	
	
	public LandingView(IController controller) {
		this.controller = controller;
		this.bpane = new BorderPane();
		this.bpane.setCenter(loginPane());
	}
	
	private GridPane loginPane() {
		GridPane pane = new GridPane();
		
		TextField idField = new TextField();
		idField.setPromptText("Your Employee ID");
		
		
		Button loginButton = new Button("Login");
		loginButton.setOnAction(e -> {
			int id = Integer.parseInt(idField.getText());
			login(id);
		});
		
		idField.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ENTER) {
				this.login(Integer.parseInt(idField.getText()));
			}
		});
		
		
		pane.add(loginButton, 1, 0);
		pane.add(idField, 0, 0);
		return pane;
	}
	
	private void login(int id) {
		System.out.println("[ph] logging in as id: " + id);
		//check if the id belongs to a driver
		List<Driver> drivers = this.controller.readAllDrivers();
		drivers.forEach(e -> {
			if(e.getEmployeeID() == id) {
				System.out.println("logged in as a driver");
				this.controller.changeView(Main.DRIVER_VIEW, e);
				return;
			}
		});
		
		//check if the id belongs to a HrManager
		List<HrManager> managers = this.controller.readAllHrManagers();
		managers.forEach(e -> {
			if(e.getEmployeeID() == id) {
				System.out.println("logged in as a manager");
				this.controller.changeView(Main.HR_VIEW, e);
				return;
			}
		});
		
	}
	


	@Override
	public BorderPane getLandingView() {
		return this.bpane;
	}
	
}
