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

public class HRView {

	private IController controller;
	private BorderPane bpane;
	private ObservableList<Driver> drivers;

	private ListView<Driver> lv;
	
	//test
	private TextField driverNameTextF;
	private TextField driversLicenseTextF;

	public HRView(IController controller) {
		this.controller = controller;
		this.bpane = new BorderPane();
		this.bpane.setLeft(driverInfo());
	}

	public GridPane driverInfo() {
		GridPane grid = new GridPane();
		Text title = new Text("Drivers:");
		
		VBox driverBox = new VBox();
		driverBox.setPadding(new Insets(20));
		
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
			updateDrivers(this.controller.readAllDrivers());
		});
		
		addDriverBox.getChildren().addAll(driverNameBox, driversLicenseBox, addDriverBtn);
		
		Text driverInfo = new Text("Driver Info: ");
		Text name = new Text("");
		Text driversLicense = new Text("");;
		Text driverID = new Text("");;
		
		
		Button deleteDriver = new Button("Remove");
		deleteDriver.setOnAction(e -> {
			this.controller.deleteDriver(lv.getSelectionModel().getSelectedItem());
			updateDrivers(this.controller.readAllDrivers());
		});
		
		
		
		driverBox.getChildren().addAll(driverInfo, name, driverID, driversLicense, deleteDriver);

		drivers = FXCollections.observableArrayList();
		lv = new ListView<>();
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
		grid.add(addDriverBox, 2, 1);

		return grid;
	}
	
	

	public BorderPane getHRView() {
		return this.bpane;
	}

	public void updateDrivers(Collection<Driver> drivers) {
		this.drivers.clear();
		this.drivers.addAll(drivers);
	}

}
