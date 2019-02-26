package view;

import java.util.Collection;

import javafx.scene.layout.BorderPane;
import model.Driver;
import model.DrivingShift;

public interface IDriverView {
	public void updateDrivers(Collection<Driver> drivers);
	public BorderPane getDriverView();
	public void updateShifts(Collection<DrivingShift> shifts);
}
