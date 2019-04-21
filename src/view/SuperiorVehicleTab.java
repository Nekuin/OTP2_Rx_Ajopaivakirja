package view;

import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.Vehicle;
import util.Strings;

/**
 * Handles SupoeriorView Vehicle Tab
 * @author Nekuin
 *
 */
public class SuperiorVehicleTab implements UndoObserver {
	
	private IController controller;
	private Strings strings;
	private Text[] vehInfoTexts;
	private TableView<Vehicle> vehicleTableView;
	private ObservableList<Vehicle> vehicles;
	
	/**
	 * Constructor
	 * @param controller
	 * @param brandText
	 * @param modelText
	 * @param regPlateText
	 * @param addVehicleButton
	 * @param updateVehicleButton
	 * @param deleteVehicleButton
	 * @param vehicleTableView
	 */
	public SuperiorVehicleTab(IController controller, Text brandText, Text modelText, Text regPlateText, Button addVehicleButton, Button updateVehicleButton, Button deleteVehicleButton, TableView<Vehicle> vehicleTableView) {
		this.controller = controller;
		strings = Strings.getInstance();
		this.vehicleTableView = vehicleTableView;
		//store Text nodes in an array for easy updates
		vehInfoTexts = new Text[] {brandText, modelText, regPlateText};
		//create listeners for buttons
		createButtonListeners(addVehicleButton, updateVehicleButton, deleteVehicleButton);
		//setup colums in the TableView
		setupColumns();
		//clear placeholder texts from info panel
		updateInfoTexts("", "", "");
	}

	/**
	 * Create listeners for all buttons
	 * @param addVehicleButton Button
	 * @param updateVehicleButton Button
	 * @param deleteVehicleButton Button
	 */
	private void createButtonListeners(Button addVehicleButton, Button updateVehicleButton,
			Button deleteVehicleButton) {
		addVehicleButton.setOnAction(e -> {
			
		});
		
		updateVehicleButton.setOnAction(e -> {
			
		});
		
		deleteVehicleButton.setOnAction(e -> {
			
		});
		
	}
	
	/**
	 * Setup and populate TableView columns
	 * Create onClick listener for the items
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
			if(clicked != null) {
				updateInfoTexts(clicked.getBrand(), clicked.getModel(), clicked.getRegNr());
			}
		});
	}
	/**
	 * Updates Vehicle info panel texts
	 * String in index 0 will update brandText, index 1 modelText
	 * and index 2 regPlateText
	 * @param strings
	 */
	private void updateInfoTexts(String...strings) {
		int i = 0;
		for(String str : strings) {
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

}
