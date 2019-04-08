package view;

import java.io.IOException;
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
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import model.Cargo;
import model.Client;
import model.DrivingShift;
import util.Strings;

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
    
    private BorderPane root;
    private ObservableList<Cargo> selectedCargoList;
	
	public AddShiftView(IController controller) {
		this.controller = controller;
		this.strings = Strings.getInstance();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AddShiftView.fxml"), strings.getBundle());
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
	
	private void populateCargoBox() {
		selectedCargoList = FXCollections.observableArrayList();
		cargo_listView.setItems(selectedCargoList);
		ObservableList<Cargo> cargoList = FXCollections.observableArrayList();
		cargoList.addAll(controller.readAllUnassignedCargo());
		cargo_combobox.setItems(cargoList);
		cargo_combobox.setOnAction(e -> {
			Cargo selected = cargo_combobox.getSelectionModel().getSelectedItem();
			if(selected != null) {
				selectedCargoList.add(selected);
				if(cargo_listView.getOpacity() < 1) {
					cargo_listView.setOpacity(1);
				}
				Platform.runLater(() -> {
					cargo_combobox.getSelectionModel().clearSelection();
				});
			}
		});
	}
	
	private void populateClientBox() {
		ObservableList<Client> clientList = FXCollections.observableArrayList();
		clientList.addAll(controller.readAllClients());
		client_combobox.setItems(clientList);
	}
	
	private void confirmAction(ActionEvent event) {
		List<Cargo> cargo = selectedCargoList.stream().collect(Collectors.toList());
		Client client = client_combobox.getSelectionModel().getSelectedItem();
		if(client == null) {
			System.out.println("no client");
			return;
		}
		if(cargo.size() == 0) {
			System.out.println("no cargo");
			return;
		}
		DrivingShift shift = new DrivingShift();
		controller.createDrivingShift(shift);
		cargo.forEach(c -> {
			shift.addCargo(c);
			c.setShift(shift);
		});
		shift.setClient(client);
		controller.updateDrivingShift(shift);
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	@Override
	public BorderPane getView() {
		return root;
	}
	
	
}
