package view;

import controller.IController;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Driver;
import util.Strings;

public class AddDriverModal {
	
	private BorderPane modalPane;
	private Strings strings;
	private IController controller;
	// test
	private TextField driverNameTextF;
	private TextField driversLicenseTextF;
	
	public AddDriverModal(IController controller) {
		this.strings = Strings.getInstance();
		this.controller = controller;
		modalPane = new BorderPane();
		setup();	
	}
	
	private void setup() {
	// label
	VBox labelBox = new VBox();
	labelBox.setSpacing(30);
	labelBox.setPadding(new Insets(30, 20, 20, 20));
	Text label = new Text("Give drivers information");
	labelBox.getChildren().add(label);

	// drivers name
	HBox driverNameBox = new HBox();
	driverNameBox.setSpacing(20);
	driverNameBox.setPadding(new Insets(20, 20, 20, 20));
	Text nameText = new Text("Drivers name:  ");
	driverNameTextF = new TextField();
	driverNameBox.getChildren().addAll(nameText, driverNameTextF);

	// drivers license
	HBox driversLicenseBox = new HBox();
	driversLicenseBox.setSpacing(20);
	driversLicenseBox.setPadding(new Insets(20, 20, 20, 20));
	Text licenseText = new Text("Drivers license:  ");
	driversLicenseTextF = new TextField();
	driversLicenseBox.getChildren().addAll(licenseText, driversLicenseTextF);

	// confirm button
	Button addDriver = new Button("Confirm");
	addDriver.setOnAction(e -> {
		Driver d = new Driver(driverNameTextF.getText(), driversLicenseTextF.getText());
		this.controller.createDriver(d);
		driverNameTextF.setText("");
		driversLicenseTextF.setText("");
		((Node) e.getSource()).getScene().getWindow().hide();
	});

	// cancel button
	Button cancelButton = new Button("Cancel");
	cancelButton.setOnAction(e -> {
		((Node) e.getSource()).getScene().getWindow().hide();
	});

	// button box
	HBox buttons = new HBox();
	buttons.setSpacing(30);
	buttons.setPadding(new Insets(30, 30, 30, 30));
	buttons.getChildren().addAll(addDriver, cancelButton);

	GridPane pane = new GridPane();
	pane.add(labelBox, 0, 0);
	pane.add(driverNameBox, 0, 1);
	pane.add(driversLicenseBox, 0, 2);
	pane.add(buttons, 0, 3);
	modalPane.setBottom(pane);
	
	}
	
	public BorderPane getAddDriverModal() {
		return modalPane;
	}

	
}

