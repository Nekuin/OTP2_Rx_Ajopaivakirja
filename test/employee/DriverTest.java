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
		controller = new Controller(null,true);
	}
	
	/**
	 * Creates a new driver before each test
	 */
	@BeforeEach
	public void resetDriver() {
		testdriver = new Driver("Kalle", "AB", false);
		
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
	 * Test for driver id setter
	 */
	@Test
	@DisplayName("Driver ID")
	void testID() {
		testdriver.setEmployeeID(11);
		assertEquals(11, testdriver.getEmployeeID(), "Driver ID did not change!");
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
	}
	
	/**
	 * Tests assigning shift
	 */
	@Test
	@DisplayName("Assign shift")
	void testAssign() {
		DrivingShift shift = new DrivingShift(new Client(), LocalDate.now());
		controller.assignShift(testdriver, shift);
		assertEquals(testdriver, shift.getShiftDriver(), "Driver was not assigned.");
	}
	
	/**
	 * Tests searching driver with employee id
	 */
	@Test
	@DisplayName("Search driver with id")
	void findDriverWithID() {
		testdriver.setEmployeeID(100);
		assertEquals(testdriver, controller.readDriver(100), "Driver was not found");
	}
	
	/**
	 * Tests updating driver information
	 */
	@Test
	@DisplayName("Update driver info")
	void updateDriver() {
		controller.createDriver(testdriver);
		testdriver.setEmployeeID(100);
		testdriver.setName("Kimmo");
		controller.updateDriver(testdriver);
		assertEquals("Kimmo", controller.readDriver(100).getName());
	}
	
	/**
	 * Tests deleting driver
	 */
	@Test
	@DisplayName("Delete driver")
	void testDelete(){
		int id = testdriver.getEmployeeID();
		controller.deleteDriver(testdriver);
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
	
	
}
