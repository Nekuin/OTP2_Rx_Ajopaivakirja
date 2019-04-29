package view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
 * Modal for adding or editing a DrivingShift
 * @author Nekuin
 *
 */
public class AddShiftView implements ViewModule {
	
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
    private SubmitObserver observer;
    
	/**
	 * Constructor
	 * @param controller
	 * @param observer
	 */
	public AddShiftView(IController controller, SubmitObserver observer) {
		this.controller = controller;
		this.strings = Strings.getInstance();
		this.observer = observer;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/xml/AddShiftView.fxml"), strings.getBundle());
		loader.setController(this);
		
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		root = loader.getRoot();
	}
	
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
		
	}
	
	/**
	 * Set items in the Cargo selection ComboBox, create onAction listener for items
	 */
	private void populateCargoBox() {
		selectedCargoList = FXCollections.observableArrayList();
		cargo_listView.setItems(selectedCargoList);
		ObservableList<Cargo> cargoList = FXCollections.observableArrayList();
		cargoList.addAll(controller.readAllUnassignedCargo());
		cargo_combobox.setItems(cargoList);
		//onAction listener, adds cargo to a list and clears the selection after adding one
		cargo_combobox.setOnAction(e -> {
			Cargo selected = cargo_combobox.getSelectionModel().getSelectedItem();
			if(selected != null) {
				//block duplicate entries
				if(!selectedCargoList.contains(selected)) {
					selectedCargoList.add(selected);
					createRemoveButton(selected);
				}
				if(cargo_listView.getOpacity() < 1) {
					cargo_listView.setOpacity(1);
				}
				//clear selection, must be run on UI thread
				Platform.runLater(() -> {
					cargo_combobox.getSelectionModel().clearSelection();
					
				});
			}
		});
	}
	
	/**
	 * Create a remove button next to an added cargo
	 * @param cargo added Cargo
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
		});
		//add button next to the item on the list
		listItem_remove_box.getChildren().add(button);
	}
	
	/**
	 * Add clients to the client ComboBox
	 */
	private void populateClientBox() {
		ObservableList<Client> clientList = FXCollections.observableArrayList();
		clientList.addAll(controller.readAllClients());
		client_combobox.setItems(clientList);
	}
	
	/**
	 * Submit button action
	 * @param event
	 */
	private void confirmAction(ActionEvent event) {
		List<Cargo> cargo = selectedCargoList.stream().collect(Collectors.toList());
		Client client = client_combobox.getSelectionModel().getSelectedItem();
		LocalDate deadline = deadlinePicker.getValue();
		
		boolean validate = validateConfirmAction(cargo, client, deadline);
		//don't continue if validation fails, validate method displays errors to the user
		if(validate == false) {
			return;
		}
		
		//create a new Shift and add cargo, shift and deadline to it
		DrivingShift shift = new DrivingShift();
		controller.createDrivingShift(shift);
		cargo.forEach(c -> {
			shift.addCargo(c);
			c.setShift(shift);
		});
		shift.setClient(client);
		shift.setDeadline(deadline);
		controller.updateDrivingShift(shift);
		//notify client that the user has submitted a new DrivingShift successfully
		observer.notifyListener();
		//hide modal
		((Node) event.getSource()).getScene().getWindow().hide();
	}
	
	/**
	 * Check that the users inputs are valid
	 * @param event
	 * @param cargo selected cargo as List
	 * @param client selected Client
	 * @param deadline selected deadline
	 * @return boolean
	 */
	private boolean validateConfirmAction(List<Cargo> cargo, Client client, LocalDate deadline) {
		if(cargo.size() == 0) {
			System.out.println("no cargo");
			ErrorTooltip.showErrorTooltip(confirm_button, strings.getString("sup_shift_cargo_err"));
			return false;
		}
		if(client == null) {
			System.out.println("no client");
			ErrorTooltip.showErrorTooltip(confirm_button, strings.getString("sup_shift_client_err"));
			return false;
		}
		if(deadline == null) {
			System.out.println("no deadline");
			ErrorTooltip.showErrorTooltip(confirm_button, strings.getString("sup_shift_date_err"));
			return false;
		}
		if(deadline.isBefore(LocalDate.now())) {
			System.out.println("in da past");
			ErrorTooltip.showErrorTooltip(confirm_button, strings.getString("sup_shift_past_err"));
			return false;
		}
		//all tests are passed, return true
		return true;
	}

	@Override
	public BorderPane getView() {
		return root;
	}
	
	
}
