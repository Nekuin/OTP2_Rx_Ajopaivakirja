package employee;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import model.*;

@ExtendWith(MockitoExtension.class)
public class HRManagerTest {
	
	static HrManager testHR;
	@Mock
	static AjoAccessObject a;
	
	@BeforeAll
	static void setup() {
		System.out.println("testing");
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
		IDriver driver = new Driver("Kalle Kaahaaja", 1001, "C1");
		when(a.createDriver(driver)).thenReturn(true);
		//when(testHR.addDriver("Kalle Kaahaaja", 1001, "C1")).thenReturn(true);
		boolean test = testHR.addDriver("Kalle Kaahaaja", 1001, "C1");
		assertEquals(true, test, "Creating the driver failed!");
	}
}
