package view;

import java.util.Collection;

import application.Main;
import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Driver;
import model.DrivingShift;
import javafx.scene.Node;

public class DriverView {
	
	
	private IController controller;
	private BorderPane bpane;
	private ObservableList<DrivingShift> shifts;
	private Driver driver;
	
	private Text driverSelection;
	private Text shiftSelection;
	private ListView<DrivingShift> shiftListView;
	private Text driverInfo;
	private DrivingShift clicked;
	
	
	public DriverView(IController controller) {
		this.controller = controller;
		bpane = new BorderPane();
		bpane.setLeft(driverInfo());
		bpane.setRight(shiftInfo());
		bpane.setCenter(assignmentPanel());
	}
	

	private GridPane shiftInfo() {
		GridPane grid = new GridPane();
		Text title = new Text("Shift info");
		
		shifts = FXCollections.observableArrayList();
		shiftListView = new ListView<>();
		shiftListView.setItems(shifts);
		
		shiftListView.setOnMouseClicked(e -> {
			clicked = shiftListView.getSelectionModel().getSelectedItem();
			//System.out.println("clicked on: " + clicked + ", reserved: " + clicked.getShiftTaken());
			if(clicked.getShiftTaken()) {
				this.shiftSelection.setText("shift: " + clicked.getShiftID() + " already taken");
			} else {
				this.shiftSelection.setText("shift: " + clicked.getShiftID());
			}
			
		});
		
		grid.add(title, 0, 0);
		grid.add(shiftListView, 0, 1);
		return grid;
	}
	
	private GridPane driverInfo() {
		GridPane grid = new GridPane();
		this.driverInfo = new Text("");
		if(driver != null) {
			this.driverInfo.setText("Driver info: " + driver);
		} else {
			this.driverInfo.setText("not logged in");
		}
		
		grid.add(driverInfo, 0, 0);
		return grid;
		
	}
	
	private GridPane assignmentPanel() {
		GridPane pane = new GridPane();
		driverSelection = new Text("");
		Button assignmentButton = new Button("Reserve shift");
		Button reportDrivingShiftButton = new Button("Report the shift");
		shiftSelection = new Text("");
		
		reportDrivingShiftButton.setOnAction(e ->{
			Stage stage = new Stage();
		  
		    stage.setScene(new Scene(new ReportingView(controller, clicked).getReportingView()));
		    stage.setTitle("Report your shift");
		    stage.initModality(Modality.APPLICATION_MODAL);
		    stage.initOwner(
		        ((Node)e.getSource()).getScene().getWindow() );
		    stage.show();
		});
		
		assignmentButton.setOnAction(e -> {
			Driver driver = this.driver;
			DrivingShift shift = this.shiftListView.getSelectionModel().getSelectedItem();
			System.out.println("[PH] assigning driver " + driver.getEmployeeID() + " to shift " + shift.getShiftID());
			this.driverSelection.setText("");
			this.shiftSelection.setText("");
			this.controller.assignShift(driver, shift);
			this.updateDriver();
			
		});

		pane.add(driverSelection, 0, 0);
		pane.add(assignmentButton, 0, 1);
		pane.add(shiftSelection, 0, 2);
		pane.add(reportDrivingShiftButton, 0, 3);
		
		return pane;
	}
	
	public void updateShifts(Collection<DrivingShift> shifts) {
		this.shifts.clear();
		this.shifts.addAll(shifts);
	}


	public BorderPane getDriverView() {
		return this.bpane;
	}


	public void updateDriver() {
		this.driver = this.controller.readDriver(Main.LOGGED_IN_ID);
		this.driverInfo.setText("Driver info: " + this.driver);
	}
	
	public void setNavBar(NavBar nav) {
		this.bpane.setTop(nav.getNavBar());
	}

	
}
