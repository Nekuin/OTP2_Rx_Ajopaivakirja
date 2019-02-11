package model;

import java.util.List;

//testi

public interface IAjo {
	
	//public Driver[] readDriver();
	public List<IDriver> readDriver();
	
	public boolean createDriver(IDriver driver);
	public Driver readDriver(String driversLicense);
	
	public boolean updateDriver(IDriver driver);
	public boolean deleteDriver(int driversLicense);

}
