package model;

import java.util.List;

//testi

public interface IAjo {
	
	//public Driver[] readDriver();
	public List<Driver> readDriver();
	
	public boolean createDriver(Driver driver);
	public Driver readDriver(String driversLicense);
	
	public boolean updateDriver(Driver driver);
	public boolean deleteDriver(int driversLicense);

}
