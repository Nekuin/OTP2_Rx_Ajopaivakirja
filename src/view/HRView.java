package view;

import java.util.Collection;

import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.Driver;

public class HRView {

	private IController controller;
	private BorderPane bpane;
	private ObservableList<Driver> drivers;

	private ListView<Driver> lv;

	public HRView(IController controller) {
		this.controller = controller;
		this.bpane = new BorderPane();
		this.bpane.setLeft(driverInfo());
	}

	public GridPane driverInfo() {
		GridPane grid = new GridPane();
		Text title = new Text("Drivers:");

		drivers = FXCollections.observableArrayList();
		lv = new ListView<>();
		lv.setItems(drivers);

		lv.setOnMouseClicked(e -> {
			Driver clicked = lv.getSelectionModel().getSelectedItem();

		});

		grid.add(title, 0, 0);
		grid.add(lv, 0, 1);

		return grid;
	}

	public BorderPane getHRView() {
		return this.bpane;
	}

	public void updateDrivers(Collection<Driver> drivers) {
		this.drivers.clear();
		this.drivers.addAll(drivers);
	}

}
