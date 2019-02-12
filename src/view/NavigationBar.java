package view;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NavigationBar {
	
	private HBox hbox;
	private BorderPane root;
	
	public NavigationBar(BorderPane root) {
		this.root = root;
		this.hbox = createNavBar();
		this.hbox.getStyleClass().add("navbar");
	}
	
	private HBox createNavBar() {
		HBox box = new HBox();
		
		Button navButton1 = new Button("Driver view");
		Button navButton2 = new Button("Second view");
		
		navButton1.setOnAction(e -> {
			//change view n stuff
		});
		
		navButton2.setOnAction(e -> {
			//change view here too n stuff
		});
		
		navButton1.getStyleClass().add("navButton");
		navButton2.getStyleClass().add("navButton");
		
		box.getChildren().addAll(navButton1, navButton2);
		return box;
	}
	
	public HBox getNavigationBar() {
		return this.hbox;
	}
}
