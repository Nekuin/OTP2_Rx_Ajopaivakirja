package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.util.Collection;

import application.Main;
import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Driver;
import model.DrivingShift;

/**
 * 
 * NOT GOING TO BE USED
 */
public class PersonalShiftView implements ViewModule {
	
	private BorderPane bpane;
	private IController controller;
	private DrivingShift drivingShift;
	private Text shiftInfo;
	private Text driverSelection;
	private Text shiftSelection;
	private DrivingShift clicked;
	private Driver driver;
	
	private ObservableList<DrivingShift> shifts;
	private ListView<DrivingShift> shiftListView;
	
	public PersonalShiftView(IController controller) {
		this.controller = controller;
		this.bpane = new BorderPane();
		this.bpane.setCenter(shiftInfo());
		bpane.setRight(assignmentPanel());
		
	}
	
	

	private GridPane assignmentPanel() {
		
		GridPane grid = new GridPane();
		Button assignButton = new Button("Unassign");
		assignButton.setTooltip(new Tooltip("Unassign/release the selected shift"));
		Button reportButton = new Button("Report Shift");
		reportButton.setTooltip(new Tooltip("Report you current Shift/Log"));
		shiftSelection = new Text("");
		
		assignButton.setOnAction(e->{
			
			Driver driver = this.driver;
			DrivingShift shift = this.shiftListView.getSelectionModel().getSelectedItem();
			System.out.println("[PH] assigning driver " + driver.getEmployeeID() + " to shift " + shift.getShiftID());
			this.driverSelection.setText("");
			this.shiftSelection.setText("");
			this.controller.assignShift(driver, shift);
			this.updateDriver();
		});
		
		reportButton.setOnAction(e->{
			
			Stage stage = new Stage();
		//	stage.setScene(new Scene(reportHandlingView()));
			stage.setTitle("Reporting target shift: ");
		});
		
		grid.add(shiftSelection, 0, 0);
		grid.add(assignButton, 0, 1);
		grid.add(reportButton, 0, 2);
		
		return grid;
	}



	public GridPane shiftInfo() {
		GridPane grid = new GridPane();
		Text title = new Text("Shifts: ");
		
		shifts = FXCollections.observableArrayList();
		shiftListView = new ListView<>();
		shiftListView.setItems(shifts);
		
		shiftListView.setOnMouseClicked(e -> {
			clicked = shiftListView.getSelectionModel().getSelectedItem();
			/**if(clicked.getShiftTaken()) {
				this.shiftSelection.setText("shift: " + clicked.getShiftID() + " already taken");
			} else {
				this.shiftSelection.setText("shift: " + clicked.getShiftID());
			}**/
			
		});
		
		grid.add(title, 0, 0);
		grid.add(shiftListView, 0, 1);
		
		return grid;
	}


	
	public void updateShifts(Collection<model.DrivingShift> shifts) {
		this.shifts.clear();
		this.shifts.addAll(shifts);
	}
	
	public void updateDriver() {
		this.driver = this.controller.readDriver(Main.LOGGED_IN_ID);
	}


	@Override
	public BorderPane getView() {
		return this.bpane;
	}

}
