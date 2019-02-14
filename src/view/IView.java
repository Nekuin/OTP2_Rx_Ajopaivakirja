package view;

import java.util.Collection;

import model.DrivingShift;
import model.IDriver;
import model.IDrivingShift;

public interface IView {
	public void setDriverData(Collection<IDriver> drivers);
	public void changeView(int view);
	public void setShiftData(Collection<IDrivingShift> shifts);
}
