package view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import model.Driver;
import model.IDriver;

public class DriverView {
	
	private BorderPane bpane;
	ObservableList<String> drivers;
	
	public DriverView() {
		bpane = new BorderPane();
		bpane.setLeft(driverTitle());
		bpane.setRight(driverInfo());
		Button update = new Button("update");
		update.setOnAction(e -> {
			List<String> names = new ArrayList<>();
			names.add("asd");
			names.add("assdsd");
			this.updateDrivers(names);
		});
		bpane.setBottom(update);
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

	
	public void updateDrivers(Collection<String> names) {
		drivers = FXCollections.observableArrayList(names);
	}
}
