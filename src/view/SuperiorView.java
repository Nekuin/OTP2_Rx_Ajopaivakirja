package view;

import controller.IController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Vehicle;

public class SuperiorView {

	private IController controller;
	private BorderPane borderPane;
	private ListView<Vehicle> lv;
	private NavBar navBar;
	
	public SuperiorView(IController controller) {
		this.controller = controller;
		this.borderPane = new BorderPane();
		setup();
	}
	
	public void setup() {
		
		VBox viewBox = new VBox();
		viewBox.setPadding(new Insets(30, 30, 30, 30));
		viewBox.getChildren().addAll(addButtons());
		viewBox.setAlignment(Pos.TOP_CENTER);
		this.borderPane.setCenter(viewBox);		
	}
	
	public void setNavBar(NavBar navBar) {
		this.navBar = navBar;
	}
	
	private ListView getCarList() {
		
		return this.lv;
	}
	
	private GridPane addButtons() {
		
		GridPane buttonPane = new GridPane();

		Button addCarBtn = new Button("Add new car");
		addCarBtn.setOnAction(e -> {
								
					//Tarkistus ja onnistumisen jäläkeen alert
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("The new car successfully added");
					alert.setContentText("You have successfully added a new car: \n"); // tähän auton tietojen haku
					alert.showAndWait();
		});
		
		Button deleteCarBtn = new Button("Delete car");
		Button updateCarBtn = new Button("Update car");

		VBox buttonBox = new VBox();
		buttonBox.setPadding(new Insets(20, 20, 20, 20));
		buttonBox.setSpacing(20);
		buttonBox.getChildren().addAll(addCarBtn, deleteCarBtn, updateCarBtn);
		buttonPane.add(buttonBox, 0, 0);
		return buttonPane;
		
	}
	
	public BorderPane getSuperiorView() {
		return this.borderPane;
	}
}
