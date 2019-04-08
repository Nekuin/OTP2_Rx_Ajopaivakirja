package view;

import javafx.scene.layout.BorderPane;
/**
 * Interface for all View modules
 * @author Nekuin
 *
 */
public interface ViewModule {
	/**
	 * Returns the root of the View module
	 * @return BorderPane borderpane
	 */
	public BorderPane getView();
}
