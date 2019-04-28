package view;

import java.io.IOException;
import java.util.Collection;
import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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

/**
 * View module for the HR managers
 * @author tuoma
 *
 */
public class HRView implements ViewModule, UndoObserver {

	private IController controller;
	private BorderPane bpane;
	private ObservableList<Driver> drivers;
	private ObservableList<DrivingShift> reportedShifts;
	private Strings strings;
	private Driver clicked;
	private DrivingShift clickedReport;

	@FXML
	private Button addDriverBtn;
	@FXML
	private Button updateDriverBtn;
	@FXML
	private Button deleteDriverBtn;
	@FXML
	private TableView<Driver> hr_tableView;
	@FXML
	private Text driver_name;
	@FXML
	private Text driver_ID;
	@FXML
	private Text driver_licence;
	@FXML
	private CheckBox hazardous_box;
	@FXML
	private Text finishtime_info;
	@FXML
	private Text starttime_info;
	@FXML
	private Text date_info;
	@FXML
	private Text deadline_info;
	@FXML
	private Text car_info;
	@FXML
	private Text driver_info;
	@FXML
	private Text shift_id;
	@FXML
	private Text client_name;
	@FXML
	private Text cargoTotalWeight;
	@FXML
	private TableView<DrivingShift> hr_ReportsTableView;
	/**
	 * Constructor which takes a controller as parameter
	 * 
	 * @param controller instance of Controller
	 */
	public HRView(IController controller) {
		drivers = FXCollections.observableArrayList();
		reportedShifts = FXCollections.observableArrayList();
		strings = Strings.getInstance();
		this.controller = controller;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/xml/HrView.fxml"), strings.getBundle());
		loader.setController(this);
		try {
			loader.load();
			bpane = loader.getRoot();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setupDriverColumns();
		setupReportColumns();
	}

	/**
	 * Initializes the Hr-managers view
	 * Creates  the stage
	 * Adds table with all drivers
	 * Adds buttons for updating, adding and deleting drivers
	 */
	@FXML
	private void initialize() {
			
		hazardous_box.setDisable(true);
		
		addDriverBtn.setOnAction(e -> {
			Stage stage = new Stage();
			stage.setScene(new Scene(DriverModal.createAddModal(controller).getView()));
			stage.setTitle(strings.getString("addModal_driver_text"));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(((Node) e.getSource()).getScene().getWindow());
			stage.show();
			stage.setOnHidden(e1 -> {
				hr_tableView.setItems(getDriversInfo());
			}); 
		});

		deleteDriverBtn.setOnAction(e -> {
			if (clicked != null) {
				UndoPopup pop = new UndoPopup(this.controller, clicked, this);
				pop.showMessage();
				drivers.remove(clicked);
				clicked = null;
			}
		});

	
		hr_tableView.setOnMouseClicked(e -> {
			clicked = hr_tableView.getSelectionModel().getSelectedItem();
			if (clicked != null) {
				updateDriverInfo();
			}
		});
		
		hr_ReportsTableView.setOnMouseClicked(e1 ->{
			clickedReport = hr_ReportsTableView.getSelectionModel().getSelectedItem();
			if(clickedReport != null) {
				updateReportsInfo();
			}
		});

		updateDriverBtn.setOnAction(e -> {
			if(clicked != null) {
				Stage stage = new Stage();
				stage.setScene(new Scene(DriverModal.createEditModal(controller, clicked).getView()));
				stage.setTitle(strings.getString("edit_driver_txt"));
				stage.initOwner(((Node)e.getSource()).getScene().getWindow());
				stage.show();
				stage.setOnHidden(event -> {
					hr_tableView.setItems(getDriversInfo());
					hr_tableView.getSelectionModel().clearSelection();
					updateDriverInfo();
				});
			}
		});

	}

	private void updateReportsInfo() {
		client_name.setText(clickedReport.getClient().getName());
		shift_id.setText(Integer.toString(clickedReport.getShiftID()));
		cargoTotalWeight.setText(Double.toString(clickedReport.getTotalCargoWeight()) + " kg");
		driver_info.setText(clickedReport.getShiftDriver().getName() + " (ID: " + clickedReport.getShiftDriver().getEmployeeID() + ")");
		car_info.setText(clickedReport.getVehicle().toString());
		deadline_info.setText(clickedReport.getDeadline().toString());
		date_info.setText(clickedReport.getDrivenDate().toString());
		starttime_info.setText(clickedReport.getStartTime());
		finishtime_info.setText(clickedReport.getFinishTime());
	}

	/**
	 * Gets information of all the drivers
	 * @return ObservableList<Driver>
	 */
	private ObservableList<Driver> getDriversInfo() {
		this.drivers.clear();
		this.drivers.addAll(controller.readAllDrivers());
		return drivers;
	}

	/**
	 * Updates the drivers information
	 */
	private void updateDriverInfo() {
		driver_name.setText(clicked.getName());
		driver_ID.setText(Integer.toString(clicked.getEmployeeID()));
		driver_licence.setText(clicked.getDriversLicense());
		if (clicked.canDriveHazardous()) {
			hazardous_box.setSelected(true);
		}else {
			hazardous_box.setSelected(false);
		}
	}

	/**
	 * Sets up the columns for the driver table
	 */
	private void setupDriverColumns() {

		TableColumn<Driver, ?> nameCol = hr_tableView.getColumns().get(0);
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Driver, ?> IDCol = hr_tableView.getColumns().get(1);
		IDCol.setCellValueFactory(new PropertyValueFactory<>("employeeID"));

		TableColumn<Driver, ?> licenceCol = hr_tableView.getColumns().get(2);
		licenceCol.setCellValueFactory(new PropertyValueFactory<>("driversLicense"));

		TableColumn<Driver, ?> shiftsCol = hr_tableView.getColumns().get(3);
		shiftsCol.setCellValueFactory(new PropertyValueFactory<>("shiftsReserved"));

		hr_tableView.setItems(getDriversInfo());
	}
	
	private void setupReportColumns() {
		
		TableColumn<DrivingShift, ?> clientNameCol = hr_ReportsTableView.getColumns().get(0);
		clientNameCol.setCellValueFactory(new PropertyValueFactory<>("client"));

		TableColumn<DrivingShift, ?> shiftIDCol = hr_ReportsTableView.getColumns().get(1);
		shiftIDCol.setCellValueFactory(new PropertyValueFactory<>("shiftID"));

		TableColumn<DrivingShift, ?> cargoCol = hr_ReportsTableView.getColumns().get(2);
		cargoCol.setCellValueFactory(new PropertyValueFactory<>("totalCargoWeight"));

		TableColumn<DrivingShift, ?> deadlineCol = hr_ReportsTableView.getColumns().get(3);
		deadlineCol.setCellValueFactory(new PropertyValueFactory<>("deadline"));
		
	}

	public void updateReports() {
		this.reportedShifts.clear();
		this.reportedShifts.addAll(controller.readReportedShifts());		
		hr_ReportsTableView.setItems(reportedShifts);
	}

	/**
	 * Update the driver list with a Collection of Drivers
	 * @param drivers collection of Drivers
	 */
	public void updateDrivers(Collection<Driver> drivers) {
		this.drivers.clear();
		this.drivers.addAll(drivers);
	}

	/**
	 * Get the whole HRView module
	 * @return BorderPane
	 */
	@Override
	public BorderPane getView() {
		return this.bpane;
	}

	/**
	 * Observer calls this method to update driver list
	 */
	@Override
	public void notifyUndo() {
		updateDrivers(this.controller.readAllDrivers());

	}
}
