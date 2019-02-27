package view;

import application.Main;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NavigationBar {
	
	private HBox hbox;
	private IView view;
	
	public NavigationBar(IView view) {
		this.view = view;
		this.hbox = createNavBar();
		this.hbox.getStyleClass().add("navbar");
	}
	
	private HBox createNavBar() {
		HBox box = new HBox();
		
		Button driverViewButton = new Button("Driver view");
		Button hrViewButton = new Button("HR view");
		
		driverViewButton.setOnAction(e -> {
			this.view.changeView(Main.DRIVER_VIEW);
		});
		
		hrViewButton.setOnAction(e -> {
			this.view.changeView(Main.HR_VIEW);
		});
		
		driverViewButton.getStyleClass().add("navButton");
		hrViewButton.getStyleClass().add("navButton");
		
		box.getChildren().addAll(driverViewButton, hrViewButton);
		return box;
	}
	
	public HBox getNavigationBar() {
		return this.hbox;
	}
}
