package employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.IController;
import model.*;

public class HRManagerTest {
	
	static HrManager testHR;
	static Driver kuski;
	static IController controller;

	@BeforeAll
	static void setup() {
		System.out.println("testing HR manager");
		controller = new Controller(null, true);
		
	}

	@BeforeEach
	void resetHR() {
		testHR = new HrManager("HR Heikki");
	}
	
	@Test
	@DisplayName("HR name")
	void testName() {
		assertEquals("HR Heikki", testHR.getName(), "Name is not correct");
	}
	
	@Test
	@DisplayName("Hr database")
	void createHrManager() {
		HrManager mng = new HrManager("Makke Manageeri");
		controller.createHrManager(mng);
		List<HrManager> managers = controller.readAllHrManagers();
		assertTrue(managers.contains(mng));
	}
	
	@Test
	@DisplayName("Hr name change")
	void changeName() {
		testHR.setName("HR Helena");
		assertEquals("HR Helena", testHR.getName(), "Name is not correct");
	}
	

}
