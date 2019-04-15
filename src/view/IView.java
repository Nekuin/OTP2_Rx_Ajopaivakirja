package view;

import javafx.scene.layout.BorderPane;

/**
 * Interface for views
 *
 */
public interface IView {
	public void changeView(int view);
	public void setUndoMessage(BorderPane root);
	public void resetRootBottom();
	public void createViews();
}
