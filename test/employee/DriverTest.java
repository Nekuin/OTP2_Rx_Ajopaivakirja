package employee;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.IController;
import model.*;

public class DriverTest {
	
	
	static Driver testdriver;
	static IController controller;

	
	@BeforeAll
	public static void setup() {
		System.out.println("testing");
		controller = new Controller(null,true);
	}
	
	@BeforeEach
	public void resetDriver() {
		testdriver = new Driver("Kalle", "AB");
	}
	
	@Test
	@DisplayName("Driver ID")
	void testID() {
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
	@DisplayName("Can drive hazardous?")
	void canDriveHazardous() {
		assertEquals(false,testdriver.canDriveHazardous(),"This driver should not be able to drive hazardous!");
	}
	
	@Test
	@DisplayName("Set can drive hazardous")
	void setCanDriveHazardous() {
		testdriver.setCanDriveHazardous(true);
		assertEquals(true,testdriver.canDriveHazardous(),"This driver should be able to drive hazardous!");
	}
	
	
}
