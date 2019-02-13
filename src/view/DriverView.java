package view;

import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.DrivingShift;
import model.IDriver;

public class DriverView implements IDriverView{
	
	private BorderPane bpane;
	private ObservableList<IDriver> drivers;
	private ObservableList<DrivingShift> shifts;
	
	public DriverView() {
		bpane = new BorderPane();
		GridPane leftGrid = new GridPane();
		Text text = new Text("informaatiota");
		leftGrid.add(text, 0, 0);
		leftGrid.add(driverInfo(), 0, 1);
		
		bpane.setLeft(leftGrid);
		bpane.setRight(shiftInfo());
	}
	

	private GridPane shiftInfo() {
		GridPane grid = new GridPane();
		Text title = new Text("Shift info");
		
		ListView<DrivingShift> lv = new ListView<>();
		lv.setItems(shifts);
		
		grid.add(title, 0, 0);
		grid.add(lv, 0, 1);
		return grid;
	}
	
	private VBox driverInfo() {
		VBox vbox = new VBox();
		drivers = FXCollections.observableArrayList();
		
		ListView<IDriver> lv = new ListView<>();
		lv.setItems(drivers);
		
		lv.setOnMouseClicked(e -> {
			IDriver clicked = lv.getSelectionModel().getSelectedItem();
			System.out.println("clicked on: " + clicked);
		});
		
		vbox.getChildren().add(lv);
		return vbox;
	}


	@Override
	public void updateDrivers(Collection<IDriver> drivers) {
		this.drivers.addAll(drivers);
	}


	@Override
	public BorderPane getDriverView() {
		return this.bpane;
	}

	
}
