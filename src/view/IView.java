package view;

import java.util.Collection;

import model.DrivingShift;
import model.IDriver;

public interface IView {
	public void setDriverData(Collection<IDriver> drivers);
	public void changeView(int view);
	public void setShiftData(Collection<DrivingShift> shifts);
}
