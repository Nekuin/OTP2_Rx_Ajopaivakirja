package view;

import java.io.IOException;
import application.Main;
import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
	
	@FXML private Text client_Name;
	@FXML private Text shift_id;
	@FXML private Text cargo_info;
	@FXML private Text time_info2;
	@FXML private Text client_Name1;
	@FXML private Text shift_id1;
	@FXML private Text cargo_info1;
	@FXML private Text time_info3;
	
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
			clicked = reserve_tableview.getSelectionModel().getSelectedItem();
			if(clicked != null) {
				updateReserveShiftInfo();
			}
		});
		
		TableColumn<DrivingShift, ?> clienCol = report_tableview.getColumns().get(1);
		clienCol.setCellValueFactory(new PropertyValueFactory<>("client"));
		
		
		report_tableview.setOnMouseClicked(e -> {
			clicked = reserve_tableview.getSelectionModel().getSelectedItem();
			if(clicked != null) {
				updateReportShiftInfo();
			}
		});
		

	}
	
	private void updateReportShiftInfo() {
		client_Name1.setText(clicked.getClient().getName());
		shift_id1.setText(Integer.toString(clicked.getClient().getClientID()));
		cargo_info1.setText(clicked.getTotalCargoWeight() + " Kg");
		time_info3.setText(clicked.getStartTime());
	}

	private void updateReserveShiftInfo() {
		client_Name.setText(clicked.getClient().getName());
		shift_id.setText(Integer.toString(clicked.getClient().getClientID()));
		cargo_info.setText(clicked.getTotalCargoWeight() + " Kg");
		time_info2.setText(clicked.getStartTime());
	}
	
	public void unassignShift() {
		clicked.setShiftDriver(null);
		clicked.setShiftTaken(false);
		//Listasta poisto samalla
	}

	public ObservableList<DrivingShift> getUpdatetdShifts(){
		this.shifts.clear();
		this.shifts.addAll(controller.readAllDrivingShifts());
		return shifts;
	}
	
	public void assignShift(ActionEvent e){
		updateDriver();
		this.controller.assignShift(driver, clicked);
		reserve_tableview.setItems(getUpdatetdShifts());
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
