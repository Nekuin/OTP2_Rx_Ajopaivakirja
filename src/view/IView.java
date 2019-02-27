package view;

import java.util.Collection;

import model.Driver;
import model.DrivingShift;


public interface IView {
	public void setDriverData(Collection<Driver> drivers);
	public void changeView(int view, Object employee);
	public void setShiftData(Collection<DrivingShift> shifts);
	public void updateDriver(Driver driver);
}
