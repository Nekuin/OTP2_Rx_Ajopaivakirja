package view;

import java.io.IOException;

import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.DrivingShift;
import util.Strings;

public class SuperiorShiftView implements ViewModule, UndoObserver {
	
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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Superior_Employee_View.fxml"), strings.getBundle());
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
		});
		
		delete_shift_button.setOnAction(e -> {
			DrivingShift shift = shiftList.getSelectionModel().getSelectedItem();
			if(shift != null) {
				UndoPopup p = new UndoPopup(controller, shift, this);
				controller.showUndoMessage(p.getView());
				controller.deleteShift(shift);
			}
			updateShiftList();
		});
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
	
}
