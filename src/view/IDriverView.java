package view;

import java.util.Collection;

import javafx.scene.layout.BorderPane;
import model.Driver;
import model.DrivingShift;

public interface IDriverView {
	public void updateDriver(Driver driver);
	public BorderPane getDriverView();
	public void updateShifts(Collection<DrivingShift> shifts);
	public void setEmployee(Driver driver);
}
