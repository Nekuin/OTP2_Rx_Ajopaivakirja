package driver;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.*;

class DriverTest {

	@Test
	@DisplayName("Make driver")
	void createDriver() {
		IDriver driver = new Driver("Kalle", 1, "AB");
		AjoAccessObject a = new AjoAccessObject();
		
		boolean test = a.createDriver((Driver)driver);
		
		assertEquals(true, test, "Creating the driver failed!");
	}

}
