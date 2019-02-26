package cargo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Cargo;

public class CargoTest {
	
	static Cargo testCargo;
	
	@BeforeEach
	void resetCargo() {
		testCargo = new Cargo(2300, false);
	}
	
	@Test
	@DisplayName("Test cargoID")
	void getCargoID() {	
		testCargo.setCargoID(7001);
		assertEquals(7001, testCargo.getCargoID(), "Cargo ID not correct.");
	}
	
	@Test
	@DisplayName("Test setCargoID")
	void setCargoID() {	
		testCargo.setCargoID(8002);
		assertEquals(8002, testCargo.getCargoID(), "Cargo ID not changed.");
	}
	
	@Test
	@DisplayName("Test getWeight")
	void getWeight() {	
		assertEquals(2300, testCargo.getWeight(), "Cargo weight not correct.");
	}
	
	@Test
	@DisplayName("Test setWeight")
	void setWeight() {	
		testCargo.setWeight(3003);
		assertEquals(3003, testCargo.getWeight(), "Cargo weight not changed.");
	}
	
	@Test
	@DisplayName("Test isHazardous")
	void isHazardous() {	
		assertEquals(false, testCargo.isHazardous(), "Cargo hazardous status not correct.");
	}
	
	
	@Test
	@DisplayName("Test setHazardous")
	void setHazardous() {
		testCargo.setHazardous(true);
		assertEquals(true, testCargo.isHazardous(), "Cargo hazardous status not changed.");
	}
}
