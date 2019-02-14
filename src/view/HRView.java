package view;

import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.IDriver;

public class HRView implements IHRView {
	
	private BorderPane bpane;
	private ObservableList<IDriver> drivers;
	
	public HRView() {
		this.bpane = new BorderPane();
		this.bpane.setLeft(driverInfo());
	}
	
	public GridPane driverInfo() {
		GridPane grid = new GridPane();
		Text title = new Text("Drivers:");
		grid.add(title, 0, 0);
		
		drivers = FXCollections.observableArrayList();
		ListView<IDriver> lv = new ListView<>();
		lv.setItems(drivers);
		grid.add(lv, 0, 1);
		
		lv.setOnMouseClicked(e -> {
			IDriver clicked = lv.getSelectionModel().getSelectedItem();
			
		});
		
		return grid;
	}
	

	@Override
	public BorderPane getHRView() {
		return this.bpane;
	}

	@Override
	public void updateDrivers(Collection<IDriver> drivers) {
		this.drivers.clear();
		this.drivers.addAll(drivers);
	}
	
}
