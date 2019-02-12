package employee;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.*;

public class HRManagerTest {
	
	static HrManager testHR;
	
	@BeforeEach
	void resetHR() {
		testHR = new HrManager("HR Heikki", 400);
	}
	
	@Test
	@DisplayName("HR name")
	void testName() {
		assertEquals("HR Heikki", testHR.getName());
	}
	
}
