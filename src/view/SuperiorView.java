package view;

import controller.IController;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class SuperiorView {

	private IController controller;
	private BorderPane borderPane;
	
	public SuperiorView(IController controller) {
		this.controller = controller;
		this.borderPane = new BorderPane();
		setup();
	}
	
	public void setup() {
		addButtons();
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
					((Node) e.getSource()).getScene().getWindow().hide();
		});
		
		Button deleteCarBtn = new Button("Delete car");
		Button updateCarBtn = new Button("Update car");

		HBox buttonHBox = new HBox();
		buttonHBox.setPadding(new Insets(0, 20, 20, 20));
		buttonHBox.getChildren().addAll(addCarBtn, deleteCarBtn, updateCarBtn);
		buttonPane.add(buttonHBox, 0, 0);
		return buttonPane;
		
	}
	
	public BorderPane getSuperiorView() {
		return this.borderPane;
	}
}
