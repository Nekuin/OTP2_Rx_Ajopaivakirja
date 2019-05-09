package view;

import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Vehicle;
import util.ErrorTooltip;
import util.Strings;

/**
 * Handles SuperiorView Vehicle Tab
 * 
 * @author Nekuin
 *
 */
public class SuperiorVehicleTab implements UndoObserver, SubmitObserver {

	private IController controller;
	private Text[] vehInfoTexts;
	private TableView<Vehicle> vehicleTableView;
	private ObservableList<Vehicle> vehicles;
	private Strings strings;

	/**
	 * Constructor
	 * 
	 * @param controller
	 * @param brandText
	 * @param modelText
	 * @param regPlateText
	 * @param cargoText
	 * @param drivenText
	 * @param maintainedText
	 * @param addVehicleButton
	 * @param updateVehicleButton
	 * @param deleteVehicleButton
	 * @param vehicleTableView
	 */
	public SuperiorVehicleTab(IController controller, Text brandText, Text modelText, Text regPlateText,
			Text drivenText, Text cargoText, Text maintainedText, Button addVehicleButton, Button updateVehicleButton,
			Button deleteVehicleButton, TableView<Vehicle> vehicleTableView) {
		this.controller = controller;
		this.vehicleTableView = vehicleTableView;
		strings = Strings.getInstance();
		// store Text nodes in an array for easy updating
		vehInfoTexts = new Text[] { brandText, modelText, regPlateText, drivenText, cargoText, maintainedText };
		// create listeners for buttons
		createButtonListeners(addVehicleButton, updateVehicleButton, deleteVehicleButton);
		// setup colums in the TableView
		setupColumns();
		// clear placeholder texts from info panel
		updateInfoTexts("", "", "", "", "", "");
	}

	/**
	 * Create listeners for all buttons
	 * 
	 * @param addVehicleButton    Button
	 * @param updateVehicleButton Button
	 * @param deleteVehicleButton Button
	 */
	private void createButtonListeners(Button addVehicleButton, Button updateVehicleButton,
			Button deleteVehicleButton) {
		addVehicleButton.setOnAction(e -> {
			addVehicle();
		});

		updateVehicleButton.setOnAction(e -> {
			updateVehicle(updateVehicleButton);
		});

		deleteVehicleButton.setOnAction(e -> {
			deleteVehicle(deleteVehicleButton);
		});

	}

	/**
	 * Add button action
	 */
	private void addVehicle() {
		Stage stage = new Stage();
		stage.setScene(new Scene(VehicleModal.createAddVehicleModal(controller, this).getView()));
		stage.setTitle(strings.getString("add_vehicle_text"));
		stage.show();
	}

	/**
	 * Update button action
	 * 
	 * @param updateVehicleButton Button
	 * @param e                   ActionEvent
	 */
	private void updateVehicle(Button updateVehicleButton) {
		Vehicle clicked = vehicleTableView.getSelectionModel().getSelectedItem();
		if (clicked != null) {
			Stage stage = new Stage();
			stage.setScene(new Scene(VehicleModal.createEditVehicleModal(controller, this, clicked).getView()));
			stage.setTitle(strings.getString("upd_vehicle_text"));
			stage.show();
		} else {
			// display error to select a vehicle first
			ErrorTooltip.showErrorTooltip(updateVehicleButton, strings.getString("sup_veh_select_err"));
		}

	}

	/**
	 * Delete button action
	 */
	private void deleteVehicle(Button deleteVehicleButton) {
		Vehicle clicked = vehicleTableView.getSelectionModel().getSelectedItem();
		if (clicked != null) {
			vehicles.remove(clicked);
			// create and show Undo popup for the user
			new UndoPopup(controller, clicked, this).showMessage();
		} else {
			ErrorTooltip.showErrorTooltip(deleteVehicleButton, strings.getString("sup_veh_select_err"));
		}
	}

	/**
	 * Setup and populate TableView columns Create onClick listener for the items
	 */
	private void setupColumns() {
		TableColumn<Vehicle, ?> brandColumn = vehicleTableView.getColumns().get(0);
		brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

		TableColumn<Vehicle, ?> regColumn = vehicleTableView.getColumns().get(1);
		regColumn.setCellValueFactory(new PropertyValueFactory<>("regNr"));

		vehicles = FXCollections.observableArrayList();
		vehicles.addAll(controller.readAllVehicles());

		vehicleTableView.setItems(vehicles);
		vehicleTableView.setOnMouseClicked(e -> {
			Vehicle clicked = vehicleTableView.getSelectionModel().getSelectedItem();
			if (clicked != null) {
				String maintained = (clicked.getMaintained()) ? strings.getString("sup_veh_yes_maintain_text")
						: strings.getString("sup_veh_no_maintain_text");
				updateInfoTexts(clicked.getBrand(), clicked.getModel(), clicked.getRegNr(),
						"" + clicked.getDrivenDistance(), "" + clicked.getMaxCargoLoad(), maintained);
			}
		});
	}

	/**
	 * Updates Vehicle info panel texts String in index 0 will update brandText,
	 * index 1 modelText, index 2 regPlateText, index 3 drivenText, index 4
	 * cargoText and index 5 maintainedText
	 * 
	 * @param strings
	 */
	private void updateInfoTexts(String... strings) {
		int i = 0;
		for (String str : strings) {
			vehInfoTexts[i].setText(str);
			i++;
		}
	}

	/**
	 * Updates the TableView list
	 */
	private void updateVehicleList() {
		vehicles.clear();
		vehicles.addAll(controller.readAllVehicles());
	}

	@Override
	public void notifyUndo() {
		updateVehicleList();
	}

	@Override
	public void notifyListener() {
		updateVehicleList();
		updateInfoTexts("", "", "", "", "", "");
	}

}
