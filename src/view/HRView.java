package view;

import java.util.Collection;

import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Driver;

/**
 * View module for the HR managers
 * @author Nekuin
 *
 */
public class HRView {

	private IController controller;
	private BorderPane bpane;
	private ObservableList<Driver> drivers;

	private ListView<Driver> lv;
	
	//test
	private TextField driverNameTextF;
	private TextField driversLicenseTextF;

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
		
		HBox driverNameBox = new HBox();
		Text nameText = new Text("Drivers name:  ");
		driverNameTextF = new TextField();
		driverNameBox.getChildren().addAll(nameText, driverNameTextF);
		
		HBox driversLicenseBox = new HBox();
		Text licenseText = new Text("Drivers license:  ");
		driversLicenseTextF = new TextField();
		driversLicenseBox.getChildren().addAll(licenseText, driversLicenseTextF);
		
		Button addDriverBtn = new Button("Add driver");
		addDriverBtn.setOnAction(e -> {
			Driver d = new Driver(driverNameTextF.getText(), driversLicenseTextF.getText());
			this.controller.createDriver(d);
			driverNameTextF.setText("");
			driversLicenseTextF.setText("");
			updateDrivers(this.controller.readAllDrivers());
		});
		
		addDriverBox.getChildren().addAll(driverNameBox, driversLicenseBox, addDriverBtn);
		
		Text driverInfo = new Text("Driver Info: ");
		TextField name = new TextField("");
		TextField driversLicense = new TextField("");;
		TextField driverID = new TextField("");;
		
		
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
			this.controller.updateDriver(clicked);
			updateDrivers(this.controller.readAllDrivers());
		});
		
		
		driverBoxButtons.getChildren().addAll(updateDriver, deleteDriver);
		
		driverBox.getChildren().addAll(driverInfo, name, driverID, driversLicense, driverBoxButtons);

		drivers = FXCollections.observableArrayList();
		lv = new ListView<>();
		lv.setMinWidth(300);
		lv.setItems(drivers);

		lv.setOnMouseClicked(e -> {
			Driver clicked = lv.getSelectionModel().getSelectedItem();
			name.setText(clicked.getName());
			driversLicense.setText(clicked.getDriversLicense());
			driverID.setText(Integer.toString(clicked.getEmployeeID()));

		});
		
		
		
		grid.add(title, 0, 0);
		grid.add(lv, 0, 1);
		grid.add(driverBox, 1, 1);
		grid.add(addDriverBox, 1, 3);

		return grid;
	}
	
	
	/**
	 * Get the whole HRView module
	 * @return BorderPane
	 */
	public BorderPane getHRView() {
		return this.bpane;
	}

	/**
	 * Update the driver list with a Collection of Drivers
	 * @param drivers collection of Drivers
	 */
	public void updateDrivers(Collection<Driver> drivers) {
		this.drivers.clear();
		this.drivers.addAll(drivers);
	}

}
