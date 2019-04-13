package view;

import java.io.IOException;
import java.util.stream.Collectors;

import application.Main;
import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	private ObservableList<DrivingShift> reserveShifts;
	private ObservableList<DrivingShift> reportShifts;
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
		reserveShifts = FXCollections.observableArrayList();
		reportShifts = FXCollections.observableArrayList();
		strings = Strings.getInstance();
		this.controller = controller;
		//get logged in Driver
		updateDriver();
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
			report_tableview.setItems(getReservedShifts());
		});

		report_button.setOnAction(e -> {
			reportShift(e);
		});
		
		unassign_button.setOnAction(e ->{
			unassignShift();
			report_tableview.setItems(getReservedShifts());
			reserve_tableview.setItems(getAvailableShifts());
		});
		
		//setup columns
		setupReserveTableColumns();
		setupReportTableColumns();
		
		reserve_tableview.setItems(getFilteredShifts());
		report_tableview.setItems(getReservedShifts());
		
		reserve_tableview.setOnMouseClicked(e -> {
			clicked = reserve_tableview.getSelectionModel().getSelectedItem();
			if(clicked != null) {
				updateReserveShiftInfo();
			}
		});
		
		report_tableview.setOnMouseClicked(e -> {
			clicked = report_tableview.getSelectionModel().getSelectedItem();
			if(clicked != null) {
				updateReportShiftInfo();
			}
		});
		

	}
	
	/**
	 * Set CellValueFactory on Report tab table columns
	 */
	private void setupReportTableColumns() {
		TableColumn<DrivingShift, ?> clienCol = report_tableview.getColumns().get(1);
		clienCol.setCellValueFactory(new PropertyValueFactory<>("client"));
		
		TableColumn<DrivingShift, ?> timeCol = report_tableview.getColumns().get(0);
		timeCol.setCellValueFactory(new PropertyValueFactory<>("deadline"));
	}
	
	/**
	 * Set CellValueFactory on Reserve tab table columns
	 */
	private void setupReserveTableColumns() {
		TableColumn<DrivingShift, ?> clientCol = reserve_tableview.getColumns().get(1);
		//"client" comes from DrivingShift instance variables
		//the string HAS TO match one of them exactly
		//client is row 56 in DrivingShift
		clientCol.setCellValueFactory(new PropertyValueFactory<>("client"));
		
		TableColumn<DrivingShift, ?> timeCol = reserve_tableview.getColumns().get(0);
		//startTime row 24 in DrivingShift
		timeCol.setCellValueFactory(new PropertyValueFactory<>("deadline"));
	}
	
	/**
	 * Update shift details in Report tab
	 */
	private void updateReportShiftInfo() {
		client_Name1.setText(clicked.getClient().getName());
		shift_id1.setText(Integer.toString(clicked.getClient().getClientID()));
		cargo_info1.setText(clicked.getTotalCargoWeight() + " Kg");
		time_info3.setText(clicked.getDeadline().toString());
	}

	/**
	 * Update shift details in Reserve tab
	 */
	private void updateReserveShiftInfo() {
		client_Name.setText(clicked.getClient().getName());
		shift_id.setText(Integer.toString(clicked.getClient().getClientID()));
		cargo_info.setText(clicked.getTotalCargoWeight() + " Kg");
		time_info2.setText(clicked.getDeadline().toString());
	}
	
	/**
	 * Unassign the selected DrivingShift from the Driver
	 */
	private void unassignShift() {
		DrivingShift selectedShift = report_tableview.getSelectionModel().getSelectedItem();
		if(selectedShift == null) {			
			return;
		}
		System.out.println("selected: " + selectedShift + " " + selectedShift.getShiftDriver());
		driver.getShift().remove(selectedShift);
		selectedShift.setShiftDriver(null);
		selectedShift.setShiftTaken(false);
		reserveShifts.remove(clicked);
	}

	/**
	 * Get DrivingShifts that are filtered by the drivers ability to drive hazardous cargo
	 * @return
	 */
	private ObservableList<DrivingShift> getFilteredShifts(){
		this.reserveShifts.clear();
		this.reserveShifts.addAll(controller.readGoodDrivingShifts(driver));
		return reserveShifts;
	}
	
	/**
	 * Get shifts that the Driver has reserved
	 * @return
	 */
	private ObservableList<DrivingShift> getReservedShifts(){
		this.reportShifts.clear();
		this.reportShifts.addAll(driver.getShift());
		return reportShifts;
	}
	
	/**
	 * Get shifts that the Driver can reserve (unreserved and filtered)
	 * @return
	 */
	private ObservableList<DrivingShift> getAvailableShifts(){
		return getFilteredShifts().stream()
				.filter(shift -> !shift.getShiftTaken())
				.collect(Collectors.toCollection(FXCollections::observableArrayList));
	}
	
	/**
	 * Assign selected DrivingShift to the Driver
	 * @param e
	 */
	private void assignShift(ActionEvent e){
		this.controller.assignShift(driver, clicked);
		reserve_tableview.setItems(getAvailableShifts());
	}
	
	/**
	 * Open reporting window for the selected DrivingShift
	 * @param e
	 */
	private void reportShift(ActionEvent e) {
		Stage stage = new Stage();
		stage.setScene(new Scene(new ReportingModal(controller, clicked).getReportingView()));
		stage.setTitle(strings.getString("report_shift_modal_title"));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(((Node) e.getSource()).getScene().getWindow());
		stage.show();
		
	}

	/**
	 * Get the logged in Driver
	 */
	private void updateDriver() {
		this.driver = this.controller.readDriver(Main.LOGGED_IN_ID);
	}

	@Override
	public BorderPane getView() {
		return this.bpane;
	}

}
