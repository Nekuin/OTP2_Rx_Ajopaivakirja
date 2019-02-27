package view;

import java.util.Collection;

import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Driver;
import model.DrivingShift;

public class DriverView implements IDriverView{
	
	
	private IController controller;
	private BorderPane bpane;
	private ObservableList<Driver> drivers;
	private ObservableList<DrivingShift> shifts;
	private Driver driver;
	
	private Text driverSelection;
	private Text shiftSelection;
	private ListView<DrivingShift> shiftListView;
	private ListView<Driver> driverListView;
	private Text driverInfo;
	
	
	public DriverView(IController controller) {
		this.controller = controller;
		bpane = new BorderPane();
		//bpane.setLeft(driverInfo());
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
			DrivingShift clicked = shiftListView.getSelectionModel().getSelectedItem();
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
	
	private GridPane driverInfo(Driver driver) {
		GridPane grid = new GridPane();
		this.driverInfo = new Text("Driver info: " + driver);
		grid.add(driverInfo, 0, 0);
		return grid;
		
	}
	
	private GridPane assignmentPanel() {
		GridPane pane = new GridPane();
		driverSelection = new Text("");
		Button assignmentButton = new Button("Reserve shift");
		shiftSelection = new Text("");
		
		assignmentButton.setOnAction(e -> {
			Driver driver = this.driver;
			DrivingShift shift = this.shiftListView.getSelectionModel().getSelectedItem();
			System.out.println("[PH] assigning driver " + driver.getEmployeeID() + " to shift " + shift.getShiftID());
			this.driverSelection.setText("");
			this.shiftSelection.setText("");
			this.controller.assignShift(driver, shift);
			
		});

		pane.add(driverSelection, 0, 0);
		pane.add(assignmentButton, 0, 1);
		pane.add(shiftSelection, 0, 2);
		
		return pane;
	}
	
	@Override
	public void updateShifts(Collection<DrivingShift> shifts) {
		this.shifts.clear();
		this.shifts.addAll(shifts);
	}


	@Override
	public BorderPane getDriverView() {
		return this.bpane;
	}


	@Override
	public void setEmployee(Driver driver) {
		this.driver = driver;
		this.bpane.setLeft(this.driverInfo(driver));
	}


	@Override
	public void updateDriver(Driver driver) {
		this.driverInfo.setText("Driver info: " + driver);
	}

	
}
