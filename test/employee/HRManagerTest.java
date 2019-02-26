package employee;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.*;

public class HRManagerTest {
	
	static HrManager testHR;
	static Driver kuski; 

	@BeforeAll
	static void setup() {
		System.out.println("testing HR manager");
		//a = new DriverAccessObject();
		
	}

	@BeforeEach
	void resetHR() {
		testHR = new HrManager("HR Heikki");
		kuski = new Driver("Asko Kuski", "B1");
	}
	
	@Test
	@DisplayName("HR name")
	void testName() {
		assertEquals("HR Heikki", testHR.getName(), "Name is not correct");
	}
	
	@Test
	@DisplayName("Test removeDriver")
	void removeDriver() {			
		//TODO: implement
	}
	
	@Test
	@DisplayName("Test addDrivingShift")
	void addDrivingShift() {			
	//	boolean test = testHR.addDrivingShift(101, "12:00", "16:00", arska);
		//assertEquals(true, test, "Adding a driver failed!");
	}
		
	@Test
	@DisplayName("Test removeDrivingShift")
	void removeDrivingShift() {			
	//	boolean test = testHR.removeDrivingShift(101, "12:00", "16:00", arska);
		//assertEquals(true, test, "Removing the driver failed!");
	}
	

}
