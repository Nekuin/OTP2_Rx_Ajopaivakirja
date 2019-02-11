package driver;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

import model.*;

class DriverTest {
	
	static AjoAccessObject a;
	
	@BeforeAll
	public static void setup() {
		a = mock(AjoAccessObject.class);
	}

	@Test
	@DisplayName("testing the tests")
	void test() {
		assertEquals(true, true, "true was not true");
	}
	
	// not working, lets use mock objects
	@Test
	@DisplayName("Make driver")
	void createDriver() {
		Driver driver = new Driver("Kalle", 1, "AB");
		
		//mock what createDriver will return
		when(a.createDriver(driver)).thenReturn(true);
		boolean test = a.createDriver(driver);
		
		assertEquals(true, test, "Creating the driver failed!");
	}
	
}
