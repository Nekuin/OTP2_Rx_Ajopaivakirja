package view;

import java.io.IOException;
import java.util.stream.Collectors;

import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DrivingShift;
import util.Strings;

public class SuperiorShiftView implements ViewModule, UndoObserver, SubmitObserver {
	
	private BorderPane bpane;
	private IController controller;
	private Strings strings;
	private ObservableList<DrivingShift> shifts;
	
    @FXML
    private Button add_shift_button;

    @FXML
    private Button delete_shift_button;

    @FXML
    private Button update_shift_button;


    private ListView<DrivingShift> shiftList;
    
    @FXML
    private VBox list_view_container;
	
	public SuperiorShiftView(IController controller) {
		this.controller = controller;
		this.strings = Strings.getInstance();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Superior_Shift_View.fxml"), strings.getBundle());
		loader.setController(this);
		try {
			loader.load();
			bpane = loader.getRoot();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setup();
	}
	
	@FXML
	private void initialize() {
		add_shift_button.setOnAction(e -> {
			System.out.println("add shift");
			showAddShiftStage(e);
		});
		
		delete_shift_button.setOnAction(e -> {
			DrivingShift shift = shiftList.getSelectionModel().getSelectedItem();
			if(shift != null) {
				UndoPopup p = new UndoPopup(controller, shift, this);
				p.showMessage();
				shifts.remove(shift);
			}
		});
		
		update_shift_button.setOnAction(e -> {
			DrivingShift shift = shiftList.getSelectionModel().getSelectedItem();
			if(shift != null) {
				showUpdateShiftStage(e, shift);
			}
		});
	}
	
	private void showUpdateShiftStage(ActionEvent e, DrivingShift shift) {
		Stage stage = new Stage();
		stage.setScene(new Scene(new UpdateShiftView(controller, shift, this).getView()));
		stage.setTitle(strings.getString("upd_shift_text"));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(((Node) e.getSource()).getScene().getWindow());
		stage.show();
	}
	
	private void showAddShiftStage(ActionEvent e) {
		Stage stage = new Stage();
		stage.setScene(new Scene(new AddShiftView(controller, this).getView()));
		stage.setTitle(strings.getString("add_shift_text"));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(((Node) e.getSource()).getScene().getWindow());
		stage.show();
	}
	
	private void setup() {
		shiftList = new ListView<>();
		shifts = FXCollections.observableArrayList();
		shiftList.setItems(shifts);
		updateShiftList();
		list_view_container.getChildren().add(shiftList);
	}
	
	
	private void updateShiftList() {
		shifts.clear();
		shifts.addAll(controller.readAllDrivingShifts());
	}

	@Override
	public BorderPane getView() {
		return bpane;
	}

	@Override
	public void notifyUndo() {
		updateShiftList();
	}

	@Override
	public void notifyListener() {
		updateShiftList();
	}
	
}
