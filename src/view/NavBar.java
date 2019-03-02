package view;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class NavBar {
	
	private IView view;
	private HBox hbox;
	
	public NavBar(IView view) {
		this.view = view;
		this.hbox = new HBox();
	}
	
	public NavBar(IView view, Button[] buttons) {
		this.view = view;
		this.hbox = new HBox();
		for(Button b : buttons) {
			this.hbox.getChildren().add(b);
		}
	}
	
	public void addNavButton(Button button) {
		this.hbox.getChildren().add(button);
	}
	
	public HBox getNavBar() {
		return this.hbox;
	}
}
