package cargo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.IController;
import model.Cargo;
import model.DrivingShift;

/**
 * Tests for cargo objects
 *
 */
public class CargoTest {
	
	static Cargo testCargo;
	static IController controller;
	
	/**
	 * Creates a new cargo object
	 */
	@BeforeEach
	void resetCargo() {
		testCargo = new Cargo(2300, false);
	}
	
	@BeforeAll
	static void init() {
		//use test version of controller by passing true as the last argument
		controller = new Controller(null, true);
	}
	
	/**
	 * Tests the setter and getter of cargo object
	 */
	@Test
	@DisplayName("Test cargoID")
	void getCargoID() {	
		testCargo.setCargoID(7001);
		assertEquals(7001, testCargo.getCargoID(), "Cargo ID not correct.");
	}
	
	/**
	 * Tests if we can create a cargo object, store it in the database
	 * and retrieve it
	 */
	@Test
	@DisplayName("Testing create to database")
	void createCargo() {
		//create a new cargo that weights 50 and is not hazardous
		Cargo cargo = new Cargo(50, false);
		controller.createCargo(cargo);
		List<Cargo> cargoList = controller.readAllCargo();
		assertTrue(cargoList.contains(cargo),"Database should have the cargo!");
	}
	
	/**
	 * Tests the setter and getter of ID of a cargo object
	 */
	@Test
	@DisplayName("Test setCargoID")
	void setCargoID() {	
		testCargo.setCargoID(8002);
		assertEquals(8002, testCargo.getCargoID(), "Cargo ID not changed.");
	}
	/**
	 * Tests the setter and getter of ID of a cargo object
	 */
	@Test
	@DisplayName("Test getWeight")
	void getWeight() {	
		assertEquals(2300, testCargo.getWeight(), "Cargo weight not correct.");
	}
	
	/**
	 * Tests the setter and getter of weight of a cargo object
	 */
	@Test
	@DisplayName("Test setWeight")
	void setWeight() {	
		testCargo.setWeight(3003);
		assertEquals(3003, testCargo.getWeight(), "Cargo weight not changed.");
	}
	
	/**
	 * Tests if the cargo is hazardous or not
	 */
	@Test
	@DisplayName("Test isHazardous")
	void isHazardous() {	
		assertEquals(false, testCargo.isHazardous(), "Cargo hazardous status not correct.");
	}
	
	/**
	 * Tests if the cargo is hazardous or not, after setting value to be true
	 */
	@Test
	@DisplayName("Test setHazardous")
	void setHazardous() {
		testCargo.setHazardous(true);
		assertEquals(true, testCargo.isHazardous(), "Cargo hazardous status not changed.");
	}
	
	@Test
	@DisplayName("Test setDrivingshift")
	void setDrivingShift() {
		DrivingShift s = new DrivingShift();
		testCargo.setShift(s);
		assertEquals(s, testCargo.getShift(), "Setting shift didn't work.");
	}
}
