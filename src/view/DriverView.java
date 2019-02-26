package view;

import java.util.Collection;

import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.IDriver;
import model.IDrivingShift;

public class DriverView implements IDriverView{
	
	
	private IController controller;
	private BorderPane bpane;
	private ObservableList<IDriver> drivers;
	private ObservableList<IDrivingShift> shifts;
	
	private Text driverSelection;
	private Text shiftSelection;
	private ListView<IDrivingShift> shiftListView;
	private ListView<IDriver> driverListView;
	
	
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
			IDrivingShift clicked = shiftListView.getSelectionModel().getSelectedItem();
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
		Text text = new Text("Driver info");
		grid.add(text, 0, 0);
		drivers = FXCollections.observableArrayList();
		
		driverListView = new ListView<>();
		driverListView.setItems(drivers);
		grid.add(driverListView, 0, 1);
		
		driverListView.setOnMouseClicked(e -> {
			IDriver clicked = driverListView.getSelectionModel().getSelectedItem();
			this.driverSelection.setText("Driver: " + clicked.getEmployeeID());
			//System.out.println("clicked on: " + clicked);
			//this.getShifts(clicked.getEmployeeID());
			//this.updateShifts(clicked.getShift());
			
		});
		return grid;
		
	}
	
	private GridPane assignmentPanel() {
		GridPane pane = new GridPane();
		driverSelection = new Text("");
		Button assignmentButton = new Button("Reserve shift");
		shiftSelection = new Text("");
		
		assignmentButton.setOnAction(e -> {
			IDriver driver = this.driverListView.getSelectionModel().getSelectedItem();
			IDrivingShift shift = this.shiftListView.getSelectionModel().getSelectedItem();
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
	public void updateDrivers(Collection<IDriver> drivers) {
		this.drivers.clear();
		this.drivers.addAll(drivers);
	}
	
	@Override
	public void updateShifts(Collection<IDrivingShift> shifts) {
		this.shifts.clear();
		this.shifts.addAll(shifts);
	}
	
	private void getShifts(int employeeID) {
		System.out.println("[PH]getting all the shifts for id: " + employeeID);
	}


	@Override
	public BorderPane getDriverView() {
		return this.bpane;
	}

	
}
