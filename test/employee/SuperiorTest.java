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
import model.Employee;
import model.Superior;
import util.TestUtil;
/**
 * 
 * @author jorin
 *
 */

public class SuperiorTest {
	
	static Superior testSuperior;
	static IController controller;
	
	@BeforeAll
	static void init() {
		//use test version of controller by passing true as the last argument
		controller = new Controller(null, TestUtil.testVersion);
	}
	
	/**
	 * Creates a Superior object
	 */
	@BeforeEach
	void resetSuperior() {
		testSuperior = new Superior("SupremeLeader");
	}
	
	/**
	 * Testing the empty constructor
	 */
	@Test
	@DisplayName("Empty constructor test")
	void emptyContructorTest() {
		Superior temp = new Superior();
		boolean test = false;
		if (temp != null) {
			test = true;
		}
		assertEquals(true, test, "Empty superior was not created.");
	}
	
	/**
	 * Tests getter of superior name
	 */
	@Test
	@DisplayName("Test getName")
	void getName() {
		assertEquals("SupremeLeader", testSuperior.getName(), "Superior name not correct.");
	}
	/**
	 * Tests getter and setter of superior name
	 */
	@Test
	@DisplayName("Test setName")
	void setName() {
		testSuperior.setName("SupermahtavaJohtaja");
		assertEquals("SupermahtavaJohtaja", testSuperior.getName(), "Superior name not changed.");
	}
	
	/**
	 * Tests the role getter
	 */
	@Test
	@DisplayName("Test role")
	void testRole() {
		assertEquals("Superior", testSuperior.getRole(), "Role is not correct!");
	}

	
	/**
	 * Tests if we can create a superior object, store it in the database
	 * and retrieve it
	 */
	@Test
	@DisplayName("Testing create to database")
	void createSuperior() {
		//create a new superior
		Superior spr = new Superior("MadMax");
		controller.createSuperior(spr);
		List<Superior> superiorList = controller.readAllSuperiors();
		assertTrue(superiorList.contains(spr),"Database should have the superior!");
		controller.deleteEmployee(spr);
	}

}
