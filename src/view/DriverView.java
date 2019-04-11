package view;

import java.io.IOException;
import java.util.Collection;
import java.util.ResourceBundle;

import application.Main;
import controller.IController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Client;
import model.Driver;
import model.DrivingShift;
import util.Strings;
import javafx.scene.Node;

public class DriverView implements ViewModule{

	private IController controller;
	private BorderPane bpane;
	private ObservableList<DrivingShift> shifts;
	private Driver driver;
	private Strings strings;
	
	@FXML
	private Button reserve_button;

	@FXML
	private Button report_button;

	@FXML
	private Button unassign_button;

	@FXML
	private TableView<DrivingShift> reserve_tableview;
	
	@FXML
	private TableView<DrivingShift> report_tableview;

	@FXML
	private TableColumn<DrivingShift, String> shift_time;

	@FXML
	private TableColumn<DrivingShift, String> customer_name;
	
	@FXML
	private Text driver_Info;

	private Text driverSelection;
	private Text shiftSelection;
	private ListView<DrivingShift> shiftListView;
	private DrivingShift clicked;

	public DriverView(IController controller) {
		shifts = FXCollections.observableArrayList();
		strings = Strings.getInstance();
		this.controller = controller;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Driver_view.fxml"), strings.getBundle());
		loader.setController(this);
		try {
			loader.load();
			bpane = loader.getRoot();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void initialize() {
		reserve_button.setOnAction(e -> {
			assignShift(e);
		});

		report_button.setOnAction(e -> {
			reportShift(e);
		});
		
		unassign_button.setOnAction(e ->{
			unassignShift();
		});
		
		//setup columns
		TableColumn<DrivingShift, ?> clientCol = reserve_tableview.getColumns().get(1);
		//"client" comes from DrivingShift instance variables
		//the string HAS TO match one of them exactly
		//client is row 56 in DrivingShift
		clientCol.setCellValueFactory(new PropertyValueFactory<>("client"));
		
		TableColumn<DrivingShift, ?> timeCol = reserve_tableview.getColumns().get(0);
		//startTime row 24 in DrivingShift
		timeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
		reserve_tableview.setItems(getUpdatetdShifts());
		
		//example on click event - OnAction doesn't seem to exist in tables
		reserve_tableview.setOnMouseClicked(e -> {
			DrivingShift clicked = reserve_tableview.getSelectionModel().getSelectedItem();
			if(clicked != null) {
				System.out.println("clicked: " + clicked);
			}
		});
		

	}
	
	public void unassignShift() {
		clicked.setShiftDriver(null);
		//Listasta poisto samalla
	}

	public ObservableList<DrivingShift> getUpdatetdShifts(){
		this.shifts.clear();
		this.shifts.addAll(controller.readAllDrivingShifts());
		return shifts;
	}
	
	public void assignShift(ActionEvent e){
		Driver driver = this.driver;
		DrivingShift shift = this.shiftListView.getSelectionModel().getSelectedItem();
		System.out.println("[PH] assigning driver " + driver.getEmployeeID() + " to shift " + shift.getShiftID());
		this.driverSelection.setText("");
		this.shiftSelection.setText("");
		this.controller.assignShift(driver, shift);
		this.updateDriver();
	}
	
	public void reportShift(ActionEvent e) {
		Stage stage = new Stage();
		stage.setScene(new Scene(new ReportingView(controller, clicked).getReportingView()));
		stage.setTitle("Report your shift");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(((Node) e.getSource()).getScene().getWindow());
		stage.show();
	}

	public void updateDriver() {
		this.driver = this.controller.readDriver(Main.LOGGED_IN_ID);
	}

	@Override
	public BorderPane getView() {
		return this.bpane;
	}

}
