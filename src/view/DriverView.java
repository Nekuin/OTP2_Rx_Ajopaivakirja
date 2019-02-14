package view;

import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.DrivingShift;
import model.IDriver;

public class DriverView implements IDriverView{
	
	private BorderPane bpane;
	private ObservableList<IDriver> drivers;
	private ObservableList<DrivingShift> shifts;
	
	public DriverView() {
		bpane = new BorderPane();

		
		bpane.setLeft(driverInfo());
		bpane.setRight(shiftInfo());
	}
	

	private GridPane shiftInfo() {
		GridPane grid = new GridPane();
		Text title = new Text("Shift info");
		
		shifts = FXCollections.observableArrayList();
		ListView<DrivingShift> lv = new ListView<>();
		lv.setItems(shifts);
		
		grid.add(title, 0, 0);
		grid.add(lv, 0, 1);
		return grid;
	}
	
	private GridPane driverInfo() {
		GridPane grid = new GridPane();
		Text text = new Text("Driver info");
		grid.add(text, 0, 0);
		drivers = FXCollections.observableArrayList();
		
		ListView<IDriver> lv = new ListView<>();
		lv.setItems(drivers);
		grid.add(lv, 0, 1);
		
		lv.setOnMouseClicked(e -> {
			IDriver clicked = lv.getSelectionModel().getSelectedItem();
			System.out.println("clicked on: " + clicked);
			this.getShifts(clicked.getEmployeeID());
		});
		return grid;
		
	}


	@Override
	public void updateDrivers(Collection<IDriver> drivers) {
		this.drivers.clear();
		this.drivers.addAll(drivers);
	}
	
	@Override
	public void updateShifts(Collection<DrivingShift> shifts) {
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
