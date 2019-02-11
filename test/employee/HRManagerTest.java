package employee;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.*;


public class HRManagerTest {
	
	static HrManager testHR;
	static AjoAccessObject a;
	
	@BeforeAll
	static void setup() {
		a = new AjoAccessObject();
	}

	@BeforeEach
	void resetHR() {
		testHR = new HrManager("HR Heikki", 400);
		testHR.setAjoAccessObject(a);
	}
	
	@Test
	@DisplayName("Test addDriver")
	void addDriver() {			
		boolean test = testHR.addDriver("Kalle Kaahaaja", 1001, "C1");
		assertEquals(true, test, "Creating the driver failed!");
	}
}
