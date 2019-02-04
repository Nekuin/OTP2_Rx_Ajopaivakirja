package view;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NavigationBar {
	
	private HBox hbox;
	
	public NavigationBar() {
		this.hbox = createNavBar();
		this.hbox.getStyleClass().add("navbar");
	}
	
	private HBox createNavBar() {
		HBox box = new HBox();
		
		Button navButton1 = new Button("Driver view");
		Button navButton2 = new Button("Second view");
		
		
		
		box.getChildren().add(navButton1);
		box.getChildren().add(navButton2);
		return box;
	}
	
	public HBox getNavigationBar() {
		return this.hbox;
	}
}
