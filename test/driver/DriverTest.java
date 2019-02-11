package driver;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.*;

class DriverTest {
	//kllk
	IDriver testdriver = new Driver("Kalle", 1, "AB");
	AjoAccessObject a = new AjoAccessObject();

	@Test
	@DisplayName("Make driver")
	void createDriver() {
		boolean test = a.createDriver((Driver)testdriver);
		
		assertEquals(true, test, "Creating the driver failed!");
	}
	
	@Test
	@DisplayName("Driver ID")
	void testID() {
		//IDriver driver = new Driver ("Kalle", 1, "AB");
		testdriver.setEmployeeID(11);
		
		assertEquals(11, testdriver.getEmployeeID(), "Driver ID did not change!");
	}
	
	@Test
	@DisplayName("Drivers License")
	void getDLicense() {
		
		assertEquals("AB", testdriver.getDriversLicense(), "Drivers license is not right!");
		
	}
	
	@Test
	@DisplayName("Change drivers license")
	void changeDLicense() {
		testdriver.setDriversLicense("C1");
		assertEquals("C1", testdriver.getDriversLicense(), "Drivers license has not changed!");
	}
	
	@Test
	@DisplayName("Delete driver")
	void deleteDriver() {
		
		
		//boolean test = a.deleteDriver();
		
		assertEquals(true, test, "Creating the driver failed!");
	}
}
