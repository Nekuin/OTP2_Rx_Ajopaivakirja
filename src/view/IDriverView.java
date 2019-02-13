package view;

import java.util.Collection;

import javafx.scene.layout.BorderPane;
import model.DrivingShift;
import model.IDriver;

public interface IDriverView {
	public void updateDrivers(Collection<IDriver> drivers);
	public BorderPane getDriverView();
	public void updateShifts(Collection<DrivingShift> shifts);
}
