package model;

public interface IVehicle {
	public String getRegNr();
	public void setRegNr(String regNr);
	public double getDrivenDistance();
	public void addDrivenDistance(double driven);
	public int getMaxCargoLoad();
	public void setMaxCargoLoad(int maxCargoLoad);
	public String getModel();
	public void setModel(String model);
	public String getBrand();
	public void setBrand(String brand);
	public int getCarID();
	public void setCarID(int carID);
	public boolean getMaintained();
	public void setMaintained(boolean maintained);
}
