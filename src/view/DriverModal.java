package view;

import controller.IController;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Driver;
import util.Strings;

/**
 * 
 * @author Nekuin, tuoma
 *
 */
public class DriverModal implements ViewModule {

	private BorderPane modalPane;
	private Strings strings;
	private IController controller;
	private boolean canDriveHazardous;
	private CheckBox hazaBox;
	private TextField driverNameTextF;
	private TextField driversLicenseTextF;
	private Driver driver;

	/**
	 * Constructor
	 * @param controller
	 */
	private DriverModal(IController controller) {
		this.strings = Strings.getInstance();
		this.controller = controller;
		modalPane = new BorderPane();
		setup();
	}
	
	/**
	 * Constructor
	 * @param controller
	 * @param driver
	 */
	private DriverModal(IController controller, Driver driver) {
		this.strings = Strings.getInstance();
		this.controller = controller;
		modalPane = new BorderPane();
		this.driver = driver;
		setup();
		driverNameTextF.setText(driver.getName());
		driversLicenseTextF.setText(driver.getDriversLicense());
		if(driver.canDriveHazardous()) {
			hazaBox.setSelected(true);
			canDriveHazardous = true;
		}else {
			canDriveHazardous = false;
		}
		
	}

	/**
	 * Sets up the driver modal
	 */
	private void setup() {

		// button box
		HBox buttons = new HBox();
		buttons.setSpacing(30);
		buttons.setPadding(new Insets(30, 30, 30, 30));
		buttons.getChildren().addAll(createConfirmButton(), createCancelButton());

		GridPane pane = new GridPane();
		pane.add(createTitle(), 0, 0);
		pane.add(createDriverNameBox(), 0, 1);
		pane.add(createDriversLicenseBox(), 0, 2);
		pane.add(createHazardousInfoBox(), 0, 3);
		pane.add(buttons, 0, 4);
		
		modalPane.setBottom(pane);
	}
	
	/**
	 * Creates the title for modal
	 * @return VBox labelBox
	 */
	private VBox createTitle() {
		VBox labelBox = new VBox();
		labelBox.setSpacing(30);
		labelBox.setPadding(new Insets(30, 20, 20, 20));
		Text label = new Text(strings.getString("add_driver_button"));
		labelBox.getChildren().add(label);
		return labelBox;
	}
	
	/**
	 * Creates the HBox which includes textfield for the driver name and info text
	 * @return HBox driverNameBox
	 */
	private HBox createDriverNameBox() {
		HBox driverNameBox = new HBox();
		driverNameBox.setSpacing(20);
		driverNameBox.setPadding(new Insets(20, 20, 20, 20));
		Text nameText = new Text(strings.getString("drivers_name"));
		driverNameTextF = new TextField();
		driverNameBox.getChildren().addAll(nameText, driverNameTextF);
		return driverNameBox;
	}
	
	/**
	 * Creates the HBox which includes textfield for the driver's license and info text
	 * @return HBox driversLicenceBox
	 */
	private HBox createDriversLicenseBox() {
		HBox driversLicenseBox = new HBox();
		driversLicenseBox.setSpacing(20);
		driversLicenseBox.setPadding(new Insets(20, 20, 20, 20));
		Text licenseText = new Text(strings.getString("licence_input"));
		driversLicenseTextF = new TextField();
		driversLicenseBox.getChildren().addAll(licenseText, driversLicenseTextF);
		return driversLicenseBox;
	}
	
	/**
	 * Creates the HBox that includes combobox for possible canDriveHazardous attribute
	 * @return HBox hazardousInfoBox
	 */
	private HBox createHazardousInfoBox() {
		HBox hazardousInfoBox = new HBox();
		hazardousInfoBox.setSpacing(20);
		hazardousInfoBox.setPadding(new Insets(20, 20, 20, 20));
		Text hazardousInfo = new Text(strings.getString("hazardous_input"));
		hazaBox = new CheckBox();
		hazardousInfoBox.getChildren().addAll(hazardousInfo, hazaBox);
		return hazardousInfoBox;
	}
	
	/**
	 * Creates confirm button and its functionalties
	 * @return Button addDriver
	 */
	private Button createConfirmButton() {
		Button addDriver = new Button(strings.getString("submit_button_text"));	
		addDriver.setOnAction(e -> {
			canDriveHazardous();
			//if driver is null we are in Add modal
			if(this.driver == null) {
				Driver newDriver = new Driver(driverNameTextF.getText(), driversLicenseTextF.getText(), canDriveHazardous);
				this.controller.createDriver(newDriver);
			} else {
				this.driver.setName(driverNameTextF.getText());
				this.driver.setDriversLicense(driversLicenseTextF.getText());
				this.driver.setCanDriveHazardous(canDriveHazardous);
				controller.updateDriver(this.driver);
			}
			
			((Node) e.getSource()).getScene().getWindow().hide();
		});
		return addDriver;
	}
	
	/**
	 * Checks if the hazaBox combobox is selected and changes the canDriverHazarous boolean if necessary
	 */
	private void canDriveHazardous() {
		if(hazaBox.isSelected()) {
			canDriveHazardous = true;
		}else {
			canDriveHazardous = false;
		}
	}
	
	/**
	 * Creates cancel button and its functionalties
	 * @return Button cancelButton
	 */
	private Button createCancelButton() {
		Button cancelButton = new Button(strings.getString("cancel_button_text"));
		cancelButton.setOnAction(e -> {
			((Node) e.getSource()).getScene().getWindow().hide();
		});
		return cancelButton;
	}
	
	/**
	 * Creates a new DriverModal
	 * @param controller
	 * @param driver
	 * @return DriverModal
	 */
	public static DriverModal createEditModal(IController controller, Driver driver) {
		return new DriverModal(controller, driver);
	}
	
	/**
	 * Creates a new DriverModal 
	 * @param controller
	 * @return
	 */
	public static DriverModal createAddModal(IController controller) {
		return new DriverModal(controller);
	}
	

	@Override
	public BorderPane getView() {
		return modalPane;
	}
}
