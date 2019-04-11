package view;

import java.io.IOException;
import java.util.Collection;
import application.Main;
import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Driver;
import model.DrivingShift;
import util.Strings;
import javafx.scene.Node;

public class DriverView {

	private IController controller;
	private BorderPane bpane;
	private ObservableList<DrivingShift> shifts;
	private Driver driver;
	private Strings strings;

	@FXML
	private Button reserve_button;

	@FXML
	private Button report_button;

	@FXML
	private Button unassign_button;

	@FXML
	private TableView<DrivingShift> reserve_tableview;

	@FXML
	private TableColumn<DrivingShift, String> shift_time;

	@FXML
	private TableColumn<DrivingShift, String> customer_name;
	
	@FXML
	private Text driver_Info;

	private Text driverSelection;
	private Text shiftSelection;
	private ListView<DrivingShift> shiftListView;
	private DrivingShift clicked;

	public DriverView(IController controller) {
		strings = Strings.getInstance();
		this.controller = controller;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Driver_view.fxml"), strings.getBundle());
		loader.setController(this);
		try {
			loader.load();
			bpane = loader.getRoot();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void initialize() {
		reserve_button.setOnAction(e -> {
			Driver driver = this.driver;
			DrivingShift shift = this.shiftListView.getSelectionModel().getSelectedItem();
			System.out.println("[PH] assigning driver " + driver.getEmployeeID() + " to shift " + shift.getShiftID());
			this.driverSelection.setText("");
			this.shiftSelection.setText("");
			this.controller.assignShift(driver, shift);
			this.updateDriver();
		});

		report_button.setOnAction(e -> {
				Stage stage = new Stage();
				stage.setScene(new Scene(new ReportingView(controller, clicked).getReportingView()));
				stage.setTitle("Report your shift");
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.initOwner(((Node) e.getSource()).getScene().getWindow());
				stage.show();
		});

	}

	public void updateShifts(Collection<DrivingShift> shifts) {
		this.shifts.clear();
		this.shifts.addAll(shifts);
	}

	public BorderPane getDriverView() {
		return this.bpane;
	}

	public void updateDriver() {
		this.driver = this.controller.readDriver(Main.LOGGED_IN_ID);

	}

}
