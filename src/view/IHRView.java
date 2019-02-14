package view;

import java.util.Collection;

import javafx.scene.layout.BorderPane;
import model.IDriver;

public interface IHRView {
	public BorderPane getHRView();
	public void updateDrivers(Collection<IDriver> drivers);
}
