package view;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * Navigation bar for some modules of the application
 * @author Nekuin
 *
 */
public class NavBar {
	
	private IView view;
	private HBox hbox;
	
	/**
	 * Constructor which takes a view as parameter
	 * @param view instance of View
	 */
	public NavBar(IView view) {
		this.view = view;
		this.hbox = new HBox();
	}
	
	/**
	 * Constructor which takes a view and an Array of Buttons as parameters
	 * @param view instance of View
	 * @param buttons Array of Buttons
	 */
	public NavBar(IView view, Button[] buttons) {
		this.view = view;
		this.hbox = new HBox();
		for(Button b : buttons) {
			this.hbox.getChildren().add(b);
		}
	}
	
	/**
	 * Add a Button to the Navigation bar
	 * @param button Button
	 */
	public void addNavButton(Button button) {
		this.hbox.getChildren().add(button);
	}
	
	/**
	 * Get the whole NavBar module
	 * @return HBox
	 */
	public HBox getNavBar() {
		return this.hbox;
	}
}
