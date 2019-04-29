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
/**
 * 
 * @author jorin
 *
 */
public class HRManagerTest {
	
	static HrManager testHR;
	static Driver kuski;
	static IController controller;

	/**
	 * Setup for the tests
	 * Creates test version of controller
	 */
	@BeforeAll
	static void setup() {
		System.out.println("testing HR manager");
		controller = new Controller(null, true);
		
	}

	/**
	 * Creates a hr manager before every test
	 */
	@BeforeEach
	void resetHR() {
		testHR = new HrManager("HR Heikki");
	}
	
	/**
	 * Tests name getter
	 */
	@Test
	@DisplayName("HR name")
	void testName() {
		assertEquals("HR Heikki", testHR.getName(), "Name is not correct");
	}
	
	/**
	 * Tests name setter
	 */
	@Test
	@DisplayName("Hr name change")
	void changeName() {
		testHR.setName("HR Helena");
		assertEquals("HR Helena", testHR.getName(), "Name is not correct");
	}

	
	/**
	 * Tests the role getter
	 */
	@Test
	@DisplayName("Test role")
	void testRole() {
		assertEquals("Hr Manager", testHR.getRole(), "Role is not correct!");
	}
	
	/**
	 * Tests creating hr manager to database and retrieving it
	 */
	@Test
	@DisplayName("Hr database")
	void createHrManager() {
		HrManager mng = new HrManager("Makke Manageeri");
		controller.createHrManager(mng);
		List<HrManager> managers = controller.readAllHrManagers();
		assertTrue(managers.contains(mng));
		controller.deleteEmployee(mng);
	}
	
	
	

}
