package view;

import java.util.Collection;

import javafx.scene.layout.BorderPane;
import model.Driver;
import model.DrivingShift;


public interface IView {
	public void setDriverData(Collection<Driver> drivers);
	public void changeView(int view);
	public void setShiftData(Collection<DrivingShift> shifts);
	public void setUndoMessage(BorderPane root);
	public void resetRootBottom();
}
