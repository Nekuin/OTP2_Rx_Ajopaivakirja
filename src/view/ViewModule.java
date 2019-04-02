package view;

import javafx.scene.layout.BorderPane;
/**
 * Interface for all View modules
 * @author Nekuin
 *
 */
public interface ViewModule {
	/**
	 * Set a navigation bar
	 * @param navBar instance of NavBar
	 */
	public void setNavBar(NavBar navBar);
	/**
	 * Returns the root of the View module
	 * @return BorderPane borderpane
	 */
	public BorderPane getView();
}
