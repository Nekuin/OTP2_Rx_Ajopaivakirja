package view;

import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DrivingShift;
import util.ErrorTooltip;
import util.Strings;

/**
 * Handles SuperiorView DrivingShift Tab
 * 
 * @author Nekuin
 *
 */
public class SuperiorDrivingShiftTab implements UndoObserver, SubmitObserver {

	private IController controller;
	private Text[] shiftInfoTexts;
	private TableView<DrivingShift> shiftTableView;
	private ObservableList<DrivingShift> shifts;

	/**
	 * Constructor
	 * 
	 * @param controller
	 * @param shiftClientText
	 * @param shiftIdText
	 * @param shiftCargoText
	 * @param shiftDeadlineText
	 * @param shiftAddButton
	 * @param shiftUpdateButton
	 * @param shiftDeleteButton
	 * @param shiftTableView
	 */
	public SuperiorDrivingShiftTab(IController controller, Text shiftClientText, Text shiftIdText, Text shiftCargoText,
			Text shiftDeadlineText, Button shiftAddButton, Button shiftUpdateButton, Button shiftDeleteButton,
			TableView<DrivingShift> shiftTableView) {

		this.controller = controller;
		this.shiftTableView = shiftTableView;
		// store Text nodes in an array for easy updating
		shiftInfoTexts = new Text[] { shiftClientText, shiftIdText, shiftCargoText, shiftDeadlineText };
		// clear placeholder texts
		updateInfoTexts("", "", "", "");
		// create listeners for the buttons
		createButtonListeners(shiftAddButton, shiftUpdateButton, shiftDeleteButton);
		// setup columns in the TableView
		setupColumns();

	}

	/**
	 * Setup and populate TableView columns Create onClick listener for the items
	 */
	private void setupColumns() {
		TableColumn<DrivingShift, ?> deadlineColumn = shiftTableView.getColumns().get(0);
		deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));

		TableColumn<DrivingShift, ?> clientColumn = shiftTableView.getColumns().get(1);
		clientColumn.setCellValueFactory(new PropertyValueFactory<>("client"));

		shifts = FXCollections.observableArrayList();
		shifts.addAll(controller.readAllDrivingShifts());

		shiftTableView.setItems(shifts);
		shiftTableView.setOnMouseClicked(e -> {
			DrivingShift clicked = shiftTableView.getSelectionModel().getSelectedItem();
			if (clicked != null) {
				updateInfoTexts(clicked.getClient().toString(), "" + clicked.getShiftID(),
						"" + clicked.getTotalCargoWeight() + "(kg)", clicked.getDeadline().toString());
			}
		});

	}

	/**
	 * Create listeners for all buttons
	 * 
	 * @param shiftAddButton    Button
	 * @param shiftUpdateButton Button
	 * @param shiftDeleteButton Button
	 */
	private void createButtonListeners(Button shiftAddButton, Button shiftUpdateButton, Button shiftDeleteButton) {
		shiftAddButton.setOnAction(e -> {
			addShift(e);
		});

		shiftUpdateButton.setOnAction(e -> {
			DrivingShift shift = shiftTableView.getSelectionModel().getSelectedItem();
			if (shift != null) {
				updateShift(e, shift);
			} else {
				ErrorTooltip.showErrorTooltip(shiftUpdateButton, Strings.getInstance().getString("sup_shift_err_msg"));
			}

		});

		shiftDeleteButton.setOnAction(e -> {
			DrivingShift shift = shiftTableView.getSelectionModel().getSelectedItem();
			if (shift != null) {
				deleteShift();
			} else {
				ErrorTooltip.showErrorTooltip(shiftDeleteButton, Strings.getInstance().getString("sup_shift_err_msg"));
			}
		});
	}

	/**
	 * Add button action
	 * 
	 * @param e ActionEvent
	 */
	private void addShift(ActionEvent e) {
		Stage stage = new Stage();
		stage.setScene(new Scene(new AddShiftView(controller, this).getView()));
		stage.setTitle(Strings.getInstance().getString("add_shift_text"));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(((Node) e.getSource()).getScene().getWindow());
		stage.show();
	}

	/**
	 * Update button action
	 */
	private void updateShift(ActionEvent e, DrivingShift shift) {
		Stage stage = new Stage();
		stage.setScene(new Scene(new UpdateShiftModal(controller, shift, this).getView()));
		stage.setTitle(Strings.getInstance().getString("upd_shift_text"));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(((Node) e.getSource()).getScene().getWindow());
		stage.show();
	}

	/**
	 * Delete button action
	 */
	private void deleteShift() {
		DrivingShift clicked = shiftTableView.getSelectionModel().getSelectedItem();
		if (clicked != null) {
			shifts.remove(clicked);
			// create and show Undo popup for the user
			new UndoPopup(controller, clicked, this).showMessage();
		}
	}

	/**
	 * Updates DrivingShift info panel texts String in index 0 will update
	 * shiftClientText, 1 shiftIdText 2 shiftCargoText and 3 shiftDeadlineText
	 * 
	 * @param strings
	 */
	private void updateInfoTexts(String... strings) {
		int i = 0;
		for (String str : strings) {
			shiftInfoTexts[i].setText(str);
			i++;
		}
	}

	/**
	 * Updates the TableView list
	 */
	private void updateShiftList() {
		shifts.clear();
		shifts.addAll(controller.readAllDrivingShifts());
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
