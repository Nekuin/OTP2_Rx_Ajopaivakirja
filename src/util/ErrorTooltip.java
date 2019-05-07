package util;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;

/**
 * Class for error tooltips
 * 
 * @author Nekuin
 *
 */
public class ErrorTooltip {

	// hide implicit public constructor
	private ErrorTooltip() {

	}
	
	/**
	 * Method for showing the tooltip
	 * @param parent
	 * @param message
	 */
	public static void showErrorTooltip(Node parent, String message) {
		// create the tooltip
		Tooltip tooltip = new Tooltip();
		tooltip.setText(message);
		tooltip.setStyle("-fx-font: 24 arial;");
		Tooltip.install(parent, tooltip);
		// get bounds and show tooltip near the button
		Bounds bounds = parent.localToScreen(parent.getBoundsInLocal());
		tooltip.show(parent.getScene().getWindow(), bounds.getMaxX(), bounds.getMaxY() - 12);
		// hide the tooltip after a delay
		new Thread(() -> {
			try {
				Thread.sleep(1500);
				// hiding must be run on UI thread!
				Platform.runLater(() -> {
					tooltip.hide();
					Tooltip.uninstall(parent, tooltip);
				});
			} catch (InterruptedException e1) {
				e1.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}).start();
	}
}
