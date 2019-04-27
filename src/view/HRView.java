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
	private Strings strings;
	private Driver clicked;

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

	/**
	 * Constructor which takes a controller as parameter
	 * 
	 * @param controller instance of Controller
	 */
	public HRView(IController controller) {
		drivers = FXCollections.observableArrayList();
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
		setupColumns();
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
	private void setupColumns() {

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
