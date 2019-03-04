package view;

import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.DrivingShift;

public class ReportingView {

	private BorderPane bpane;
	private IController controller;
	private DrivingShift drivingShift;
	private Text[] shiftDetails;
	private CheckBox confirmBox;

	public ReportingView(IController controller, DrivingShift drivingShift) {
		this.controller = controller;
		this.drivingShift = drivingShift;
		this.bpane = new BorderPane();
		setup();
	}

	private void setup() {
		VBox viewVBox = new VBox();
		viewVBox.setPadding(new Insets(30, 30, 30, 30));
		viewVBox.getChildren().addAll(getShiftDetails(drivingShift), getFillableInfo(), addButtons());
		viewVBox.setAlignment(Pos.TOP_CENTER);
		this.bpane.setCenter(viewVBox);
	}

	private GridPane getShiftDetails(DrivingShift drivingShift) {

		GridPane grid = new GridPane();
		VBox shiftInfoVBox = new VBox();
		shiftInfoVBox.setSpacing(20);
		shiftInfoVBox.setPadding(new Insets(20, 20, 20, 20));

		Text title = new Text("You are now reporting:");
		title.setStyle("-fx-font: 24 arial;");
		/*Text client = new Text("Client: " + drivingShift.getClient());
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

	private GridPane getFillableInfo() {
		GridPane grid = new GridPane();

		VBox labelVBox = new VBox();
		labelVBox.setSpacing(20);
		labelVBox.setPadding(new Insets(0, 20, 20, 20));
		Text label = new Text("Fill in the information below:");
		label.setStyle("-fx-font: 20 arial;");
		labelVBox.getChildren().add(label);

		HBox carHBox = new HBox();
		carHBox.setSpacing(20);
		carHBox.setPadding(new Insets(0, 20, 10, 20));
		ObservableList<String> carList = FXCollections.observableArrayList("Mese", "Toyota", "Foordi"); //tänne pitää tuoda autot
		ComboBox<String> carDropDown = new ComboBox(carList);
		carDropDown.setPrefWidth(200);
		Text carSelectText = new Text("Which car did you use?");
		carHBox.getChildren().addAll(carSelectText, carDropDown);

		VBox timeInfoVbox = new VBox();
		timeInfoVbox.setSpacing(20);
		timeInfoVbox.setPadding(new Insets(0, 20, 20, 20));
		Text startTime = new Text("Start time: String/Date");
		Text finishTime = new Text("Finish time: String/Date");
		timeInfoVbox.getChildren().addAll(startTime, finishTime);

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
		Text confirmText = new Text("Check the box before submitting the report");
		checkBeforeSubmitHBox.getChildren().addAll(confirmText, confirmBox);

		grid.add(labelVBox, 0, 0);
		grid.add(carHBox, 0, 1);
		grid.add(timeInfoVbox, 0, 2);
		grid.add(additionalInfoVBox, 0, 3);
		grid.add(checkBeforeSubmitHBox, 0, 4);

		return grid;
	}

	private GridPane addButtons() {
		GridPane buttonPane = new GridPane();

		Button submitButton = new Button("Submit");
		submitButton.setOnAction( e -> {
			if(!confirmBox.isSelected()) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Check the confirmation box");
				alert.setHeaderText("Please check the box before submitting.");
				alert.setContentText("Thank you!");
				alert.showAndWait();
			}else {
				//controller.updateDrivingShift(drivingShift);
			}
		});
		
		Button closeButton = new Button("Cancel");
		closeButton.setOnAction(e ->{		
                    //how to close the modal
        });

		HBox buttonHBox = new HBox();
		buttonHBox.setPadding(new Insets(0, 20, 20, 20));
		buttonHBox.setSpacing(20);
		buttonHBox.getChildren().addAll(closeButton, submitButton);
		buttonPane.add(buttonHBox, 0, 0);
		return buttonPane;
	}

	public BorderPane getReportingView() {
		return this.bpane;
	}

}
