package employee;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.*;

public class HRManagerTest {
	
	static HrManager testHR;
	
<<<<<<< Upstream, based on branch 'Development' of https://github.com/Nekuin/OTP1-R6.git
=======
	@BeforeAll
	static void setup() {
		System.out.println("testing");
		a = new AjoAccessObject();
		Driver arska = new Driver("Arska Ajaja", 1002, "C1");
	}

>>>>>>> df0916a Hr-manager class changes and HRManagerTest class created.
	@BeforeEach
	void resetHR() {
		testHR = new HrManager("HR Heikki");
	}
	
	@Test
	@DisplayName("HR name")
	void testName() {
		assertEquals("HR Heikki", testHR.getName());
	}
	
<<<<<<< Upstream, based on branch 'Development' of https://github.com/Nekuin/OTP1-R6.git
=======
	@Test
	@DisplayName("Test removeDriver")
	void removeDriver() {			
		boolean test = testHR.removeDriver(1002);//ei toimi
		assertEquals(true, test, "Removing the driver failed!");
	}
	
	@Test
	@DisplayName("Test addDrivingShift")
	void addDrivingShift() {			
	//	boolean test = testHR.addDrivingShift(101, "12:00", "16:00", arska);
		//assertEquals(true, test, "Removing the driver failed!");
	}
	//addDrivingShift
	//removeDrivingShift
>>>>>>> df0916a Hr-manager class changes and HRManagerTest class created.
}
