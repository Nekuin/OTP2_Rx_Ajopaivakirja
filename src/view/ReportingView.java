package view;

import java.time.LocalDate;
import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.DrivingShift;
import model.Vehicle;

/**
 * 
 * @author tuoma
 * View class for reporting
 */
public class ReportingView {

	private BorderPane bpane;
	private IController controller;
	private DrivingShift drivingShift;
	private CheckBox confirmBox;
	private ComboBox<Vehicle> carDropDown;
	private ObservableList<Vehicle> carList;
	private LocalDate localDate;
	private TextField startTimeTextF;
	private TextField finishTimeTextF;

	/**
	 * Constructor for reporting view
	 * @param controller controller of reporting view
	 * @param drivingShift drivingShift for reporting view
	 */
	public ReportingView(IController controller, DrivingShift drivingShift) {
		this.controller = controller;
		this.drivingShift = drivingShift;
		this.bpane = new BorderPane();
		setup();
	}

	/**
	 * Sets up the reporting modal
	 */
	private void setup() {
		VBox viewVBox = new VBox();
		viewVBox.setPadding(new Insets(30, 30, 30, 30));
		viewVBox.getChildren().addAll(getShiftDetails(drivingShift), getFillableInfo(), addButtons());
		viewVBox.setAlignment(Pos.TOP_CENTER);
		this.bpane.setCenter(viewVBox);
	}
	
	/**
	 * Gets the driving shift information and returns it as a grid pane
	 * @param drivingShift driving shift that has been chosen from previous view
	 * @return GridPane
	 */
	private GridPane getShiftDetails(DrivingShift drivingShift) {

		GridPane grid = new GridPane();
		VBox shiftInfoVBox = new VBox();
		shiftInfoVBox.setSpacing(20);
		shiftInfoVBox.setPadding(new Insets(20, 20, 20, 20));

		Text title = new Text("You are now reporting:");
		title.setStyle("-fx-font: 24 arial;");
		/*
		  Text client = new Text("Client: " + drivingShift.getClient().getName()); 
		  Text shift = new Text("Shift id: " + drivingShift.getShiftID()); 
		  Text cargo = new Text("Cargo: " + drivingShift.getCargo());
		 */
		Text client = new Text("Client: ");
		Text shift = new Text("Shift id: ");
		Text cargo = new Text("Cargo: ");

		shiftInfoVBox.getChildren().addAll(title, client, shift, cargo);
		grid.add(shiftInfoVBox, 0, 0);

		return grid;
	}
	
	/**
	 * Creates the fillable fields into the modal
	 * @return GridPane
	 */
	private GridPane getFillableInfo() {
		GridPane grid = new GridPane();

		VBox labelVBox = new VBox();
		labelVBox.setSpacing(20);
		labelVBox.setPadding(new Insets(0, 20, 20, 20));
		Text label = new Text("Fill in the information below:");
		label.setStyle("-fx-font: 20 arial;");
		Text mandatoryText = new Text("Fields with * are mandatory");
		mandatoryText.setStyle("-fx-font: 17 arial;");
		labelVBox.getChildren().addAll(label, mandatoryText);

		HBox carHBox = new HBox();
		carHBox.setSpacing(20);
		carHBox.setPadding(new Insets(0, 20, 0, 20));
		this.carList = FXCollections.observableArrayList();
		this.carList.addAll(controller.readAllVehicles());
		carDropDown = new ComboBox<>(carList);
		carDropDown.setPrefWidth(200);
		Text carSelectText = new Text("* Which car did you use?");
		carHBox.getChildren().addAll(carSelectText, carDropDown);

		VBox timeInfoVbox = new VBox();
		timeInfoVbox.setSpacing(20);
		timeInfoVbox.setPadding(new Insets(0, 20, 20, 20));

		HBox dateHBox = new HBox();
		dateHBox.setSpacing(20);
		dateHBox.setPadding(new Insets(20, 20, 0, 0));
		Text dateText = new Text("* Select the date:");
		DatePicker datePicker = new DatePicker();
		datePicker.showWeekNumbersProperty();
		datePicker.setOnAction(action -> {
			localDate = datePicker.getValue();
		});
		dateHBox.getChildren().addAll(dateText, datePicker);

		HBox startHBox = new HBox();
		startHBox.setSpacing(20);
		Text startTimeText = new Text("* Start time (hh:mm):  ");
		startTimeTextF = new TextField();
		startHBox.getChildren().addAll(startTimeText, startTimeTextF);

		HBox finishHBox = new HBox();
		finishHBox.setSpacing(20);
		Text finishTimeText = new Text("* Finish time (hh:mm):");
		finishTimeTextF = new TextField();
		finishHBox.getChildren().addAll(finishTimeText, finishTimeTextF);

		timeInfoVbox.getChildren().addAll(dateHBox, startHBox, finishHBox);

		VBox additionalInfoVBox = new VBox();
		additionalInfoVBox.setSpacing(10);
		additionalInfoVBox.setPadding(new Insets(0, 20, 20, 20));
		Text additionalInfoText = new Text("Additional info about the shift:");
		TextArea additionalInfo = new TextArea();
		additionalInfo.setPrefSize(300, 150);
		additionalInfo.setWrapText(true);
		additionalInfoVBox.getChildren().addAll(additionalInfoText, additionalInfo);

		HBox checkBeforeSubmitHBox = new HBox();
		checkBeforeSubmitHBox.setSpacing(20);
		checkBeforeSubmitHBox.setPadding(new Insets(0, 20, 20, 20));
		confirmBox = new CheckBox();
		Text confirmText = new Text("I have filled in the all information needed");
		checkBeforeSubmitHBox.getChildren().addAll(confirmText, confirmBox);

		grid.add(labelVBox, 0, 0);
		grid.add(carHBox, 0, 1);
		grid.add(timeInfoVbox, 0, 2);
		grid.add(additionalInfoVBox, 0, 3);
		grid.add(checkBeforeSubmitHBox, 0, 4);

		return grid;
	}
	
	
	/**
	 * Adds buttons and their functions into the grid pane
	 * @return GridPane
	 */
	private GridPane addButtons() {
		GridPane buttonPane = new GridPane();

		Button submitButton = new Button("Submit");
		submitButton.setOnAction(e -> {

			if (checkInput()) {
				if (!confirmBox.isSelected()) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Check the confirmation box");
					alert.setHeaderText(null);
					alert.setContentText("Please check the box before submitting.");
					alert.showAndWait();
				} else {
					drivingShift.setShiftDriven(true);
					drivingShift.setVehicle(carDropDown.getValue());
					drivingShift.setStartTime(startTimeTextF.getText());
					drivingShift.setFinishTime(finishTimeTextF.getText());
					controller.updateDrivingShift(drivingShift);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Successfully reported");
					alert.setContentText("You have successfully reported shift:" + drivingShift.getShiftID());
					alert.showAndWait();
				}

			} else {
				System.out.println("missing info");
			}

		});

		Button closeButton = new Button("Cancel");
		closeButton.setOnAction(e -> {
			((Node) e.getSource()).getScene().getWindow().hide();
		});

		HBox buttonHBox = new HBox();
		buttonHBox.setPadding(new Insets(0, 20, 20, 20));
		buttonHBox.setSpacing(20);
		buttonHBox.getChildren().addAll(closeButton, submitButton);
		buttonPane.add(buttonHBox, 0, 0);
		return buttonPane;
	}
	
	/**
	 * Checks the user inputs
	 * @return boolean
	 */
	public boolean checkInput() {

		final String timeRegex = "(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]";

		if (carDropDown.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Choose a car");
			alert.setHeaderText(null);
			alert.setContentText("Please choose the used car from the dropdown menu.");
			alert.showAndWait();
			return false;
		}

		if (localDate == null) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Enter the date");
			alert.setHeaderText(null);
			alert.setContentText("Please enter the date you drove the shift.");
			alert.showAndWait();
			return false;
		}

		if (startTimeTextF.getText() == null || !startTimeTextF.getText().matches(timeRegex)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Enter start time");
			alert.setHeaderText(null);
			alert.setContentText("Please enter the time (hh:mm) you started the shift.");
			alert.showAndWait();
			return false;
		}

		if (finishTimeTextF.getText() == null || !finishTimeTextF.getText().matches(timeRegex)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Enter finish time");
			alert.setHeaderText(null);
			alert.setContentText("Please enter the time (hh:mm) you finished the shift.");
			alert.showAndWait();
			return false;
		}

		return true;

	}
	
	/**
	 * returns the whole modal 
	 * @return BorderPane
	 */
	public BorderPane getReportingView() {
		return this.bpane;
	}

}
