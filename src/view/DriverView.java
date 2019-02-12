package view;

import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.IDriver;

public class DriverView implements IDriverView{
	
	private BorderPane bpane;
	private ObservableList<IDriver> drivers;
	
	public DriverView() {
		bpane = new BorderPane();
		bpane.setLeft(driverTitle());
		bpane.setRight(driverInfo());
	}
	

	private VBox driverTitle() {
		VBox vbox = new VBox();
		Text text = new Text("informaatiota");
		vbox.getChildren().add(text);
		return vbox;
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
