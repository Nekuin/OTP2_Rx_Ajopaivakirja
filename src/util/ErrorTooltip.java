package util;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;

public class ErrorTooltip {

	public static void showErrorTooltip(Node parent, String message) {
		//create the tooltip
		Tooltip tooltip = new Tooltip();
		tooltip.setText(message);
		tooltip.setStyle("-fx-font: 24 arial;");
		Tooltip.install(parent, tooltip);
		//get bounds and show tooltip near the button
		//Node node = (Node) e.getSource();
		Node node = parent;
		Bounds bounds = node.localToScreen(node.getBoundsInLocal());
		tooltip.show(node.getScene().getWindow(), bounds.getMaxX(), bounds.getMaxY()-12);
		//hide the tooltip after a delay
		new Thread(() ->  {
			try {
				Thread.sleep(1500);
				//hiding must be run on UI thread!
				Platform.runLater(() -> {
					tooltip.hide();
					Tooltip.uninstall(parent, tooltip);
				});
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}).start();
	}
}
