package drivingshift;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.IController;
import model.Cargo;
import model.Client;
import model.Driver;
import model.DrivingShift;

public class DrivingShiftTest {
	static DrivingShift testShift;
	static Driver testDriver;
	static IController controller;
	
	@BeforeAll
	public static void setup() {
		testDriver = new Driver("Kalle", "AB");
		//we don't have an instance of view => null as first parameter
		//use false as the second if you want to test locally
		//and use true if you want Jenkins to test
		controller = new Controller(null, true);
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
	
	@Test
	@DisplayName("DrivingShift DB test")
	void createDrivingShift() {
		DrivingShift s = new DrivingShift(new Client(), new Cargo(), LocalDate.now());
		controller.createDrivingShift(s);
		List<DrivingShift> shifts = controller.readAllDrivingShifts();
		assertTrue(shifts.contains(s));
	}
}
