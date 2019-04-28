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
import util.Strings;

/**
 * 
 * @author tuoma
 * View class for reporting
 */
public class ReportingModal {

	private BorderPane bpane;
	private Strings strings;
	private IController controller;
	private DrivingShift drivingShift;
	private CheckBox confirmBox;
	private ComboBox<Vehicle> carDropDown;
	private ObservableList<Vehicle> carList;
	private LocalDate localDate;
	private TextField startTimeTextF;
	private TextField finishTimeTextF;
	private DatePicker datePicker;

	/**
	 * Constructor for reporting view
	 * @param controller controller of reporting view
	 * @param drivingShift drivingShift for reporting view
	 */
	public ReportingModal(IController controller, DrivingShift drivingShift) {
		this.strings = Strings.getInstance();
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
		shiftInfoVBox.setSpacing(25);
		shiftInfoVBox.setPadding(new Insets(20, 20, 20, 20));

		Text title = new Text(strings.getString("now_repoting_title"));
		title.setStyle("-fx-font: 22 arial;");
		
		  Text client = new Text(strings.getString("customer_name") + " " + drivingShift.getClient().getName()); 
		  Text shift = new Text(strings.getString("shift_id") + " " +drivingShift.getShiftID()); 
		  Text cargo = new Text(strings.getString("cargo_text") + " " +drivingShift.getTotalCargoWeight() + " kg");
		  Text deadLine = new Text(strings.getString("deadline_info") + ": " + drivingShift.getDeadline());
		  
		shiftInfoVBox.getChildren().addAll(title, client, shift, cargo, deadLine);
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
		labelVBox.setSpacing(25);
		labelVBox.setPadding(new Insets(20, 20, 40, 20));
		Text label = new Text(strings.getString("fill_in_info"));
		label.setStyle("-fx-font: 20 arial;");
		Text mandatoryText = new Text(strings.getString("mandatory_info"));
		mandatoryText.setStyle("-fx-font: 17 arial;");
		labelVBox.getChildren().addAll(label, mandatoryText);

		HBox carHBox = new HBox();
		carHBox.setSpacing(20);
		carHBox.setPadding(new Insets(0, 20, 0, 20));
		this.carList = FXCollections.observableArrayList();
		this.carList.addAll(controller.readAllVehicles());
		carDropDown = new ComboBox<>(carList);
		carDropDown.setPrefWidth(200);
		Text carSelectText = new Text(strings.getString("which_car"));
		carHBox.getChildren().addAll(carSelectText, carDropDown);

		VBox timeInfoVbox = new VBox();
		timeInfoVbox.setSpacing(20);
		timeInfoVbox.setPadding(new Insets(0, 20, 20, 20));

		HBox dateHBox = new HBox();
		dateHBox.setSpacing(20);
		dateHBox.setPadding(new Insets(20, 20, 0, 0));
		Text dateText = new Text(strings.getString("date_field"));
		datePicker = new DatePicker();
		datePicker.showWeekNumbersProperty();
		datePicker.setOnAction(action -> {
			localDate = datePicker.getValue();
		});
		dateHBox.getChildren().addAll(dateText, datePicker);

		HBox startHBox = new HBox();
		startHBox.setSpacing(20);
		Text startTimeText = new Text(strings.getString("start_time_field"));
		startTimeTextF = new TextField();
		startHBox.getChildren().addAll(startTimeText, startTimeTextF);

		HBox finishHBox = new HBox();
		finishHBox.setSpacing(20);
		Text finishTimeText = new Text(strings.getString("finish_time_field"));
		finishTimeTextF = new TextField();
		finishHBox.getChildren().addAll(finishTimeText, finishTimeTextF);

		timeInfoVbox.getChildren().addAll(dateHBox, startHBox, finishHBox);

		VBox additionalInfoVBox = new VBox();
		additionalInfoVBox.setSpacing(10);
		additionalInfoVBox.setPadding(new Insets(0, 20, 20, 20));
		Text additionalInfoText = new Text(strings.getString("Additional_info"));
		TextArea additionalInfo = new TextArea();
		additionalInfo.setPrefSize(300, 150);
		additionalInfo.setWrapText(true);
		additionalInfoVBox.getChildren().addAll(additionalInfoText, additionalInfo);

		HBox checkBeforeSubmitHBox = new HBox();
		checkBeforeSubmitHBox.setSpacing(20);
		checkBeforeSubmitHBox.setPadding(new Insets(0, 20, 20, 20));
		confirmBox = new CheckBox();
		Text confirmText = new Text(strings.getString("confirmBox_info"));
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

		Button submitButton = new Button(strings.getString("submit_button_text"));
		submitButton.setOnAction(e -> {

			if (checkInput()) {
				if (!confirmBox.isSelected()) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle(strings.getString("confirmbox_alert_title"));
					alert.setHeaderText(null);
					alert.setContentText(strings.getString("confirmbox_alert_info"));
					alert.showAndWait();
				} else {
					drivingShift.setShiftDriven(true);
					drivingShift.setVehicle(carDropDown.getValue());
					drivingShift.setStartTime(startTimeTextF.getText());
					drivingShift.setFinishTime(finishTimeTextF.getText());
					drivingShift.setShiftReported(true);
					drivingShift.setDrivenDate(datePicker.getValue());
					controller.updateDrivingShift(drivingShift);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle(strings.getString("reproted_alert_title"));
					alert.setHeaderText(null);
					alert.setContentText(strings.getString("shift_id") + " "+drivingShift.getShiftID() + "\n" + strings.getString("customer_name") + " " + drivingShift.getClient().getName());
					alert.showAndWait();
					((Node) e.getSource()).getScene().getWindow().hide();
				}

			} else {
				System.out.println("missing info");
			}

		});

		Button closeButton = new Button(strings.getString("cancel_button_text"));
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
			alert.setTitle(strings.getString("car_missing_alert_title"));
			alert.setHeaderText(null);
			alert.setContentText(strings.getString("car_missing_alert_info"));
			alert.showAndWait();
			return false;
		}

		if (localDate == null) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(strings.getString("date_missing_alert_info"));
			alert.setHeaderText(null);
			alert.setContentText(strings.getString("date_missing_alert_info"));
			alert.showAndWait();
			return false;
		}

		if (startTimeTextF.getText() == null || !startTimeTextF.getText().matches(timeRegex)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(strings.getString("startTime_missing_alert_info"));
			alert.setHeaderText(null);
			alert.setContentText(strings.getString("startTime_missing_alert_info"));
			alert.showAndWait();
			return false;
		}

		if (finishTimeTextF.getText() == null || !finishTimeTextF.getText().matches(timeRegex)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(strings.getString("finishTime_missing_alert_info"));
			alert.setHeaderText(null);
			alert.setContentText(strings.getString("finishTime_missing_alert_info"));
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
