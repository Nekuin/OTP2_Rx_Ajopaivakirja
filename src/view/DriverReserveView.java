package view;

import java.util.Collection;
import java.util.stream.Collectors;

import application.Main;
import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Driver;
import model.DrivingShift;
import util.Strings;
/**
 * View module where the Driver can reserve shifts
 * @author Nekuin
 *
 */
public class DriverReserveView implements ViewModule{
	
	private BorderPane bpane;
	private ObservableList<DrivingShift> shifts;
	private ListView<DrivingShift> shiftListView;
	private Text[] shiftDetailTexts;
	private Strings strings;
	
	private IController controller;
	
	/**
	 * Constructor which takes the controller and navbar as arguments
	 * @param controller instance of Controller
	 * @param navBar instance of NavBar
	 */
	public DriverReserveView(IController controller, NavBar navBar) {
		strings = Strings.getInstance();
		this.controller = controller;
		this.bpane = new BorderPane();
		this.setNavBar(navBar);
		setup();
	}
	
	/**
	 * Constructor which takes the controller as argument
	 * @param controller instance of Controller
	 */
	public DriverReserveView(IController controller) {
		strings = Strings.getInstance();
		this.controller = controller;
		this.bpane = new BorderPane();
		setup();
	}
	
	/**
	 * Setup the looks of this module
	 */
	private void setup() {
		this.shiftDetailTexts = new Text[4];
		SplitPane split = new SplitPane();
		split.getItems().add(shiftPanel());
		VBox vbox = new VBox();
		vbox.getChildren().add(shiftDetailsPanel());
		
		Button reserveButton = new Button(strings.getString("driver_reserve_text"));
		reserveButton.setOnAction(e -> {
			Driver driver = this.controller.readDriver(Main.LOGGED_IN_ID);
			this.controller.assignShift(driver, shiftListView.getSelectionModel().getSelectedItem());
			this.updateShiftList(this.controller.readGoodDrivingShifts(driver));
			//clear details text
			this.updateShiftDetailText("", "", "", "");
		});
		
		vbox.getChildren().add(reserveButton);
		split.getItems().add(vbox);
		
		this.bpane.setCenter(split);
	}
	
	/**
	 * A panel where you can see a list of DrivingShifts
	 * @return VBox 
	 */
	private VBox shiftPanel() {
		VBox vbox = new VBox();
		shiftListView = new ListView<>();
		
		shifts = FXCollections.observableArrayList();
		shiftListView.setItems(shifts);
		
		shiftListView.setOnMouseClicked(e -> {
			DrivingShift selected = shiftListView.getSelectionModel().getSelectedItem();
			if(selected == null) {
				//clear details text
				this.updateShiftDetailText("", "", "", "");
				return;
			}
			//this.updateShiftDetailText(new String[] {selected.getClient().toString(), "" + selected.getShiftID(), "" + selected.getTotalCargoWeight(), "time"});
			this.updateShiftDetailText(selected.getClient().toString(), "" + selected.getShiftID(), "" + selected.getTotalCargoWeight(), "time");
		});
		
		vbox.getChildren().add(shiftListView);
		return vbox;
	}
	
	/**
	 * A panel where you can see details about a selected shift
	 * @return GridPane
	 */
	private GridPane shiftDetailsPanel() {
		GridPane grid = new GridPane();
		Text title = new Text(strings.getString("shift_details_text") + ": ");
		grid.add(title, 0, 0);
		
		Text client = new Text(strings.getString("client_text") + ": ");
		Text shift = new Text(strings.getString("shift_text") + " id: ");
		Text cargo = new Text(strings.getString("cargo_text") + ": ");
		Text time = new Text(strings.getString("time_text") + ": ");
		
		for(int i = 0; i < shiftDetailTexts.length; i++) {
			this.shiftDetailTexts[i] = new Text("");
		}
		
		grid.add(client, 0, 1);
		grid.add(shiftDetailTexts[0], 1, 1);
		grid.add(shift, 0, 2);
		grid.add(shiftDetailTexts[1], 1, 2);
		grid.add(cargo, 0, 3);
		grid.add(shiftDetailTexts[2], 1, 3);
		grid.add(time, 0, 4);
		grid.add(shiftDetailTexts[3], 1, 4);
		
		return grid;
	}
	
	/**
	 * Update shift details texts
	 * @param strings details of the shift, pass arguments in this order: client, shift id, total cargo weight and time
	 */
	public void updateShiftDetailText(String... strings) {
		for(int i = 0; i < shiftDetailTexts.length; i++) {
			shiftDetailTexts[i].setText(strings[i]);
		}
	}
	
	/**
	 * Update the list of DrivingShifts with a collection of DrivingShifts
	 * @param shifts
	 */
	public void updateShiftList(Collection<DrivingShift> shifts) {
		this.shifts.clear();
		this.shifts.addAll(shifts.stream().filter(shift -> !shift.getShiftTaken()).collect(Collectors.toList()));
	}
	
	/**
	 * Set the instance of NavBar
	 * @param navBar instance of NavBar
	 */
	public void setNavBar(NavBar navBar) {
		this.bpane.setTop(navBar.getNavBar());
	}

	/**
	 * Get the whole module
	 * @return BorderPane
	 */
	@Override
	public BorderPane getView() {
		return this.bpane;
	}

	@Override
	public void notifyUndo() {
		// TODO Auto-generated method stub
		
	}
	
}
