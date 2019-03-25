package view;

import javafx.scene.layout.BorderPane;
/**
 * Interface for all View modules
 * @author Nekuin
 *
 */
public interface ViewModule {
	public void setNavBar(NavBar navBar);
	public BorderPane getView();
}
