package view;

import java.io.IOException;
import java.time.LocalDate;

import controller.IController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.Cargo;
import model.Client;
import model.DrivingShift;
import util.ErrorTooltip;
import util.Strings;

/**
 * Modal for updating driving shifts
 * @author Nekuin
 *
 */
public class UpdateShiftModal implements ViewModule {
	
	private IController controller;
	private Strings strings;
	
    @FXML
    private ComboBox<Cargo> cargo_combobox;

    @FXML
    private ListView<Cargo> cargo_listView;

    @FXML
    private ComboBox<Client> client_combobox;

    @FXML
    private Button confirm_button;

    @FXML
    private Button cancel_button;
    
    @FXML
    private VBox listItem_remove_box;
    
    @FXML
    private DatePicker deadlinePicker;
    
    private BorderPane root;
    private ObservableList<Cargo> selectedCargoList;
    private DrivingShift shift;
    private SubmitObserver observer;
	
    /**
     * Constructor for the modal
     * @param IController controller
     * @param DrivingShift shift
     * @param SubmitObserver observer
     */
	public UpdateShiftModal(IController controller, DrivingShift shift, SubmitObserver observer) {
		this.controller = controller;
		this.strings = Strings.getInstance();
		this.shift = shift;
		this.observer = observer;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/xml/UpdateShiftView.fxml"), strings.getBundle());
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		root = loader.getRoot();
	}
	
	/**
	 * Initializes the buttons in the modal
	 */
	@FXML
	private void initialize() {
		cancel_button.setOnAction(e -> {
			((Node) e.getSource()).getScene().getWindow().hide();
		});
		
		confirm_button.setOnAction(e -> {
			confirmAction(e);
		});
		
		populateCargoBox();
		populateClientBox();
		
		//set deadline
		deadlinePicker.setValue(shift.getDeadline());
		//onAction listener that updates the shifts deadline
		deadlinePicker.setOnAction(e -> {
			LocalDate newDate = deadlinePicker.getValue();
			//check if the newly selected date is in the past
			if(newDate.isBefore(LocalDate.now())) {
				//in the past, reset value and display error
				deadlinePicker.setValue(shift.getDeadline());
				ErrorTooltip.showErrorTooltip(deadlinePicker, e, "past");
			} else {
				//not in the past, set newDate as Deadline
				shift.setDeadline(newDate);
			}
		});
	}

	/**
	 * Populates the Client ComboBox, and creates an onAction listener
	 * for setting the Client for the shift
	 */
	private void populateClientBox() {
		ObservableList<Client> clientList = FXCollections.observableArrayList();
		clientList.addAll(controller.readAllClients());
		client_combobox.setItems(clientList);
		//default the selection to the client that is already used in shift
		client_combobox.getSelectionModel().select(shift.getClient());
		client_combobox.setOnAction(e -> {
			Client selected = client_combobox.getSelectionModel().getSelectedItem();
			if(selected != null) {
				shift.setClient(selected);
			}
		});
	}


	/**
	 * Populates the Cargo ComboBox, and creates an onAction listener
	 * that adds selected cargo to the shift
	 */
	private void populateCargoBox() {
		selectedCargoList = FXCollections.observableArrayList();
		shift.getCargo().forEach(e -> {
			selectedCargoList.add(e);
			createRemoveButton(e);
		});
		cargo_listView.setItems(selectedCargoList);
		ObservableList<Cargo> cargoList = FXCollections.observableArrayList();
		cargoList.addAll(controller.readAllUnassignedCargo());
		cargo_combobox.setItems(cargoList);
		cargo_combobox.setOnAction(e -> {
			Cargo selected = cargo_combobox.getSelectionModel().getSelectedItem();
			if(selected != null) {
				//add cargo to the ListView
				selectedCargoList.add(selected);
				//add cargo to the shift object
				shift.addCargo(selected);
				//set shift to the cargo object
				selected.setShift(shift);
				if(cargo_listView.getOpacity() < 1) {
					cargo_listView.setOpacity(1);
				}
				Platform.runLater(() -> {
					cargo_combobox.getSelectionModel().clearSelection();
					createRemoveButton(selected);
				});
			}
		});
	}
	
	/**
	 * Updates the cargo in the combobox
	 */
	private void updateAvailableCargo() {
		cargo_combobox.getItems().clear();
		cargo_combobox.getItems().addAll(controller.readAllUnassignedCargo());
	}
	
	/**
	 * Creates remove button and its functionalities
	 * @param Cargo cargo
	 */
	private void createRemoveButton(Cargo cargo) {
		Button button = new Button("X");
		//mimic cell height
		button.setMaxHeight(23);
		button.setPrefHeight(23);
		button.setMinHeight(23);
		//create onAction listener
		button.setOnAction(e -> {
			//remove self
			listItem_remove_box.getChildren().remove(button);
			//remove cargo from list
			selectedCargoList.remove(cargo);
			//remove cargo from shift object
			shift.getCargo().remove(cargo);
			//remove shift from cargo object
			cargo.setShift(null);
			//update cargo in the database
			controller.updateCargo(cargo);
			//update combobox list
			updateAvailableCargo();
		});
		//add button next to the item on the list
		listItem_remove_box.getChildren().add(button);
	}
	
	/**
	 * Submits the driving shift information
	 * @param event
	 */
	private void confirmAction(ActionEvent event) {
		controller.updateDrivingShift(shift);
		observer.notifyListener();
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	@Override
	public BorderPane getView() {
		return root;
	}

}
