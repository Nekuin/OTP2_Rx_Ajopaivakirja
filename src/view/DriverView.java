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
	ObservableList<String> drivers;
	
	public DriverView() {
		bpane = new BorderPane();
		bpane.setLeft(driverTitle());
		bpane.setRight(driverInfo());
	}
	
	public BorderPane getDriverView() {
		return this.bpane;
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
		drivers.addAll("joku", "joku toinen", "hullu");
		
		ListView lv = new ListView();
		lv.setItems(drivers);
		
		vbox.getChildren().add(lv);
		return vbox;
	}

	@Override
	public void updateDrivers(Collection<IDriver> drivers) {
		
	}

	
	
	
}
