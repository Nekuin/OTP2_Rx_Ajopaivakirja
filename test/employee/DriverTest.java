package employee;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.IController;
import model.*;
import util.TestUtil;

public class DriverTest {
	
	
	static Driver testdriver;
	static IController controller;

	/**
	 * Setup for driver tests
	 * Creates test version of controller
	 */
	@BeforeAll
	public static void setup() {
		System.out.println("testing");
		controller = new Controller(null, TestUtil.testVersion);
	}
	
	/**
	 * Creates a new driver before each test
	 */
	@BeforeEach
	public void resetDriver() {
		//defaults can drive hazardous to false
		testdriver = new Driver("Kalle", "AB");
		
	}
	
	@Test
	@DisplayName("Testing the complex constructor")
	void complexConstructor() {
		Driver testD = new Driver("Sakke", "AB", true);
		assertTrue(testD.canDriveHazardous(), "Driver was not successfully created.");
	}
	
	/**
	 * Testing the empty constructor
	 */
	@Test
	@DisplayName("Empty constructor test")
	void emptyContructorTest() {
		Driver temp = new Driver();
		boolean test = false;
		if (temp != null) {
			test = true;
		}
		assertEquals(true, test, "Empty driver was not created.");
	}
	
	
	
	/**
	 * Tests the role getter
	 */
	@Test
	@DisplayName("Test role")
	void testRole() {
		assertEquals("Driver", testdriver.getRole(), "Role is not correct!");
	}
	
	/**
	 * Test for drivers license getter
	 */
	@Test
	@DisplayName("Drivers License")
	void getDLicense() {		
		assertEquals("AB", testdriver.getDriversLicense(), "Drivers license is not right!");
		
	}
	
	/**
	 * Test for drivers license setter
	 */
	@Test
	@DisplayName("Change drivers license")
	void changeDLicense() {
		testdriver.setDriversLicense("C1");
		assertEquals("C1", testdriver.getDriversLicense(), "Drivers license has not changed!");
	}
	
	/**
	 * Testing reserved shifts
	 */
	@Test
	@DisplayName("Drivers shifts")
	void shiftsReserved() {
		assertEquals(0, testdriver.getShiftsReserved(), "There sould be no reserved shifts.");
	}
	
	/**
	 * Tests getter for canDriveHazardous parameter
	 */
	@Test
	@DisplayName("Can drive hazardous?")
	void canDriveHazardous() {
		assertEquals(false,testdriver.canDriveHazardous(),"This driver should not be able to drive hazardous!");
	}
	
	/**
	 * Tests setter for canDriveHazardous parameter
	 */
	@Test
	@DisplayName("Set can drive hazardous")
	void setCanDriveHazardous() {
		testdriver.setCanDriveHazardous(true);
		assertEquals(true,testdriver.canDriveHazardous(),"This driver should be able to drive hazardous!");
	}
	
	/**
	 * Tests getter for shift list
	 */
	@Test
	@DisplayName("Get shifts")
	void getShifts() {
		DrivingShift test = new DrivingShift();
		testdriver.addDrivingShift(test);
		List<DrivingShift> testList = testdriver.getShifts();
		boolean contains = testList.contains(test);
		assertTrue(contains, "Driving shift not added.");
	}
	
	/**
	 * Tests shift that hasn't been driven
	 */
	@Test
	@DisplayName("Shift not driven")
	void testNotDriving() {
		DrivingShift shift = new DrivingShift(new Client(), LocalDate.now());
		assertEquals(false, shift.isShiftDriven(), "Shift should not be driven.");
	}
	
	/**
	 * Tests driving a shift
	 */
	@Test
	@DisplayName("Drive shift")
	void testDriving() {
		DrivingShift shift = new DrivingShift(new Client(), LocalDate.now());
		testdriver.driveShift(shift);
		assertEquals(true, shift.isShiftDriven(), "Shift was not driven.");
	}
	
	/**
	 * Tests if we can create a driver object, store it in the database
	 * and retrieve it
	 */
	@Test
	@DisplayName("Testing create to database")
	void createDriver() {
		//create a new driver
		Driver dr = new Driver("Make", "C2", true);
		controller.createDriver(dr);
		List<Driver> driverList = controller.readAllDrivers();
		assertTrue(driverList.contains(dr),"Database should have the driver!");
		controller.deleteDriver(dr);
	}
	
	/**
	 * Tests assigning shift
	 */
	@Test
	@DisplayName("Assign shift")
	void testAssign() {
		Driver dr = new Driver("Make", "C2", true);
		DrivingShift shift = new DrivingShift(new Client(), LocalDate.now());
		controller.assignShift(dr, shift);
		assertEquals(dr, shift.getShiftDriver(), "Driver was not assigned.");
		controller.deleteShift(shift);
		controller.deleteDriver(dr);
	}
	
	/**
	 * Tests updating driver information
	 */
	@Test
	@DisplayName("Update driver info")
	void updateDriver() {
		Driver dr = new Driver("Make", "C2", true);
		controller.createDriver(dr);
		int id = dr.getEmployeeID();
		dr.setName("Kimmo");
		controller.updateDriver(dr);
		assertEquals("Kimmo", controller.readDriver(id).getName());
		controller.deleteDriver(dr);
	}
	
	/**
	 * Tests deleting driver
	 */
	@Test
	@DisplayName("Delete driver")
	void testDelete(){
		Driver dr = new Driver("Make", "C2", true);
		int id = dr.getEmployeeID();
		controller.deleteDriver(dr);
		assertEquals(null, controller.readDriver(id));
	}
	
	/**
	 * Tests creating hazardous cargo and simulates driver looking for suitable shifts
	 */
	@Test
	@DisplayName("Hazardous cargo test")
	void hazardousShiftCargoTest() {
		System.out.println("haz test");
		DrivingShift hazShift = new DrivingShift(new Client("client"), LocalDate.now());
		List<Cargo> hazCargo = new ArrayList<>();
		hazCargo.add(new Cargo(50, false));
		hazCargo.add(new Cargo(70, true));
		
		hazShift.addCargo(hazCargo);
		
		controller.createDrivingShift(hazShift);
		List<DrivingShift> shifts = controller.readGoodDrivingShifts(testdriver);
		assertEquals(0, shifts.size(), "Expected no shifts in the list");
		controller.deleteShift(hazShift);
		System.out.println("/haz test");
	}
	
	/**
	 * Tests creating non hazardous cargo and simulates driver looking for suitable shifts
	 */
	@Test
	@DisplayName("Non hazardous cargo test")
	void nonHazardousShiftCargoTest() {
		System.out.println("non haz");
		DrivingShift nonHazShift = new DrivingShift(new Client("client"), LocalDate.now());
		List<Cargo> nonHazCargo = new ArrayList<>();
		nonHazCargo.add(new Cargo(50, false));
		nonHazCargo.add(new Cargo(60, false));
		
		nonHazShift.addCargo(nonHazCargo);
		
		controller.createDrivingShift(nonHazShift);
		List<DrivingShift> shifts = controller.readGoodDrivingShifts(testdriver);
		assertEquals(1, shifts.size(), "Expected shifts in the list, was: ");
		controller.deleteShift(nonHazShift);
		System.out.println("/non haz");
	}
	
	/**
	 * Tests toString method
	 */
	@Test
	@DisplayName("toString test")
	void toStringTest() {
		boolean contains = testdriver.toString().contains("License");
		assertTrue(contains, "toString method is not working properly.");
	}
	
	/**
	 * Tests driven distance setter and getter
	 */
	@Test
	@DisplayName("driven distance getter and setter")
	void drivenDistanceTest() {
		testdriver.setDrivenDistance(1000);
		assertEquals(1000, testdriver.getDrivenDistance(), "Driven distance was not correct!");
	}
	
	/**
	 * Tests adding to driven distance
	 */
	@Test
	@DisplayName("add driven distance")
	void addDrivenDistanceTest() {
		testdriver.setDrivenDistance(1000);
		testdriver.addDrivenDistance(1000);
		assertEquals(2000, testdriver.getDrivenDistance(), "Driven distance was not correct!");
	}
	
	/**
	 * Tests driven hours setter and getter
	 */
	@Test
	@DisplayName("driven hours getter and setter")
	void drivenHoursTest() {
		testdriver.setDrivenHours(1000);
		assertEquals(1000, testdriver.getDrivenHours(), "Driven hours were not correct!");
	}
	
	/**
	 * Tests adding to driven hours
	 */
	@Test
	@DisplayName(" add driven hours")
	void addDrivenHoursTest() {
		testdriver.setDrivenHours(1000);
		testdriver.addDrivenHours(1000);
		assertEquals(2000, testdriver.getDrivenHours(), "Driven hours were not correct!");
	}
	
	/**
	 * Tests driven cargo setter and getter
	 */
	@Test
	@DisplayName("driven cargo getter and setter")
	void drivenCargoTest() {
		testdriver.setDrivenCargo(1000);
		assertEquals(1000, testdriver.getDrivenCargo(), "Driven cargo was not correct!");
	}
	
	/**
	 * Tests adding to driven cargo
	 */
	@Test
	@DisplayName("add driven cargo")
	void addDrivenCargoTest() {
		testdriver.setDrivenCargo(1000);
		testdriver.addDrivenCargo(1000);
		assertEquals(2000, testdriver.getDrivenCargo(), "Driven cargo was not correct!");
	}
	
	
	
	
	
	
	
	
	
}
