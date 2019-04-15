package cargo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.IController;
import model.Cargo;

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
	void init() {
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
	
	@Test
	void createCargo() {
		//create a new cargo that weights 50 and is not hazardous
		controller.createCargo(new Cargo(50, false));
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
}
