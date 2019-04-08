package view;

import java.util.Collection;

import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Driver;

/**
 * View module for the HR managers
 * @author Nekuin
 *
 */
public class HRView implements ViewModule {

	private IController controller;
	private BorderPane bpane;
	private ObservableList<Driver> drivers;

	private ListView<Driver> lv;
	
	//test
	private TextField driverNameTextF;
	private TextField driversLicenseTextF;
	
	String cssLayoutDriverBox = "-fx-border-color: black;\n" +
            "-fx-border-insets: 5;\n" +
            "-fx-border-width: 2;\n" +
            "-fx-border-style: solid;\n";

	/**
	 * Constructor which takes a controller as parameter
	 * @param controller instance of Controller
	 */
	public HRView(IController controller) {
		this.controller = controller;
		this.bpane = new BorderPane();
		this.bpane.setLeft(driverInfo());
	}

	/**
	 * A module which displays info about the Drivers
	 * @return GridPane
	 */
	public GridPane driverInfo() {
		GridPane grid = new GridPane();
		Text title = new Text("Drivers:");
		
		VBox driverBox = new VBox();
		driverBox.setPadding(new Insets(20));
		
		HBox driverBoxButtons = new HBox();
		driverBoxButtons.setPadding(new Insets(20));
		
		VBox addDriverBox = new VBox();
		addDriverBox.setPadding(new Insets(20));
		
		
		Button addDriverBtn = new Button("Add driver");
		addDriverBtn.setOnAction(e -> {
			Stage stage = new Stage();
			stage.setScene(new Scene(handleAddDriver()));
			stage.setTitle("Add a new driver");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(((Node) e.getSource()).getScene().getWindow());
			stage.show();
		});
		
		
		
		Text driverInfo = new Text("Driver Info: ");
		TextField name = new TextField("");
		name.setPromptText("Name");
		TextField driversLicense = new TextField("");
		driversLicense.setPromptText("Drivers license");
		TextField driverID = new TextField("");
		driverID.setPromptText("Employee ID");
		
		HBox hazardousBox = new HBox();
		Text hazardousText = new Text("Can drive hazardous: ");
		CheckBox hazardousCheck = new CheckBox();
		hazardousBox.getChildren().addAll(hazardousText, hazardousCheck);
		
		
		Button deleteDriver = new Button("Remove");
		deleteDriver.setOnAction(e -> {
			this.controller.deleteDriver(lv.getSelectionModel().getSelectedItem());
			updateDrivers(this.controller.readAllDrivers());
		});
		
		Button updateDriver = new Button("Update");
		updateDriver.setOnAction(e -> {
			Driver clicked = lv.getSelectionModel().getSelectedItem();
			clicked.setName(name.getText());
			clicked.setDriversLicense(driversLicense.getText());
			
			if(hazardousCheck.isSelected()) {
				clicked.setCanDriveHazardous(true);
			} else {
				clicked.setCanDriveHazardous(false);
			}
			
			this.controller.updateDriver(clicked);
			updateDrivers(this.controller.readAllDrivers());
		});
		
		
		driverBoxButtons.getChildren().addAll(updateDriver, deleteDriver);
		
		driverBox.getChildren().addAll(driverInfo, name, driverID, driversLicense, hazardousBox, driverBoxButtons);
		
		driverBox.setStyle(cssLayoutDriverBox);
		
		drivers = FXCollections.observableArrayList();
		lv = new ListView<>();
		lv.setMinWidth(300);
		lv.setItems(drivers);

		lv.setOnMouseClicked(e -> {
			Driver clicked = lv.getSelectionModel().getSelectedItem();
			name.setText(clicked.getName());
			driversLicense.setText(clicked.getDriversLicense());
			driverID.setText(Integer.toString(clicked.getEmployeeID()));
			hazardousCheck.setSelected(clicked.getCanDriveHazardous());

		});
		
		
		
		grid.add(title, 0, 0);
		grid.add(lv, 0, 1);
		grid.add(driverBox, 1, 1);
		grid.add(addDriverBtn, 2, 2);

		return grid;
	}
	
	/**
	 * Modal for adding drivers
	 * @return BorderPane
	 */
	public BorderPane handleAddDriver() {
		
		BorderPane modalPane = new BorderPane();
		
		//label
		VBox labelBox = new VBox();
		labelBox.setSpacing(30);
		labelBox.setPadding(new Insets(30, 20, 20, 20));
		Text label = new Text("Give new drivers information");
		labelBox.getChildren().addAll(label);
		
		//drivers name
		HBox driverNameBox = new HBox();
		driverNameBox.setSpacing(20);
		driverNameBox.setPadding(new Insets(20,20,20,20));
		Text nameText = new Text("Drivers name:  ");
		driverNameTextF = new TextField();
		driverNameBox.getChildren().addAll(nameText, driverNameTextF);
		
		//drivers license
		HBox driversLicenseBox = new HBox();
		driversLicenseBox.setSpacing(20);
		driversLicenseBox.setPadding(new Insets(20,20,20,20));
		Text licenseText = new Text("Drivers license:  ");
		driversLicenseTextF = new TextField();
		driversLicenseBox.getChildren().addAll(licenseText, driversLicenseTextF);
		
		//confirm button
		Button addDriver = new Button("Confirm");
		addDriver.setOnAction(e -> {
			Driver d = new Driver(driverNameTextF.getText(), driversLicenseTextF.getText());
			this.controller.createDriver(d);
			driverNameTextF.setText("");
			driversLicenseTextF.setText("");
			updateDrivers(this.controller.readAllDrivers());
		});
		
		//cancel button
		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(e -> {
			((Node) e.getSource()).getScene().getWindow().hide();
		});
		
		//button box
		HBox buttons = new HBox();
		buttons.setSpacing(30);
		buttons.setPadding(new Insets(30,30,30,30));
		buttons.getChildren().addAll(addDriver, cancelButton);
		
		GridPane pane = new GridPane();
		pane.add(labelBox, 0, 0);
		pane.add(driverNameBox, 0, 1);
		pane.add(driversLicenseBox, 0, 2);
		pane.add(buttons, 0, 3);
		modalPane.setBottom(pane);
		
		
		return modalPane;
	}
	

	/**
	 * Update the driver list with a Collection of Drivers
	 * @param drivers collection of Drivers
	 */
	public void updateDrivers(Collection<Driver> drivers) {
		this.drivers.clear();
		this.drivers.addAll(drivers);
	}

	/**
	 * Get the whole HRView module
	 * @return BorderPane
	 */
	@Override
	public BorderPane getView() {
		return this.bpane;
	}
}
