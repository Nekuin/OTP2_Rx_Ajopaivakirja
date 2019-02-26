package drivingshift;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Driver;
import model.DrivingShift;

public class DrivingShiftTest {
	static DrivingShift testShift;
	static Driver testDriver;
	
	@BeforeAll
	public static void setup() {
		testDriver = new Driver("Kalle", "AB");
	}
	
	@BeforeEach
	public void resetDrivingShift() {
		testShift = new DrivingShift();
	}
	
	@Test
	@DisplayName("Shift ID")
	void shiftID() {
		testShift.setShiftID(1);
		assertEquals(1, testShift.getShiftID(), "Shift Id is wrong!");
	}
	
	@Test
	@DisplayName("Shift finish time")
	void shiftFinishTime(){
		testShift.setFinishTime("16:00");
		assertEquals("16:00", testShift.getFinishTime(), "Finishing time is wrong!");
	}
	
	@Test
	@DisplayName("Is shift driven?")
	void shiftIsDriven() {
		
		assertEquals(false, testShift.isShiftDriven());
	}
	
	@Test
	@DisplayName("Drive shift")
	void driveShift() {
		testShift.setShiftDriven(true);
		
		assertEquals(true, testShift.isShiftDriven(), "Shift was not driven!");
	}
	
	@Test
	@DisplayName("Shift driver")
	void shiftDriver() {
		testShift.setShiftDriver(testDriver);
		assertEquals(testDriver, testShift.getShiftDriver(), "Shift driver is not correct!");
	}
}
