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
import util.TestUtil;

/**
 * Test class for employee objects
 * @author jorin, tuoma
 *
 */
public class EmployeeTest {
	
	static Employee testEmployee;
	static IController controller;
	
	/**
	 * Setup for employee tests
	 * Creates test version of controller
	 */
	@BeforeAll
	public static void setup() {
		System.out.println("testing");
		controller = new Controller(null, TestUtil.testVersion);
	}
	
	/**
	 * Creates a new employee before each test
	 */
	@BeforeEach
	void resetEmployee() {
		testEmployee = new Employee("Röi Ukko");
	}
	
	/**
	 * Testing the empty constructor
	 */
	@Test
	@DisplayName("Empty constructor test")
	void emptyContructorTest() {
		Employee temp = new Employee();
		boolean test = false;
		if (temp != null) {
			test = true;
		}
		assertEquals(true, test, "Empty employee was not created.");
	}
	
	/**
	 * Tests getter for name
	 */
	@Test
	@DisplayName("Test getName")
	void getName() {	
		assertEquals("Röi Ukko", testEmployee.getName(), "Employee name not correct.");
	}
	
	/**
	 * Tests setter for name
	 */
	@Test
	@DisplayName("Test setName")
	void setName() {
		testEmployee.setName("Röi Akka");
		assertEquals("Röi Akka", testEmployee.getName(), "Employee name not changed.");
	}
	
	/**
	 * Tests the role getter
	 */
	@Test
	@DisplayName("Test role")
	void testRole() {
		assertEquals("", testEmployee.getRole(), "Role is not correct!");
	}
	
	/**
	 * Tests the role setter
	 */
	@Test
	@DisplayName("Set role")
	void setRole() {
		testEmployee.setRole("Rölli");
		assertEquals("Rölli", testEmployee.getRole(), "Role wasn't set.");
	}
	
	/**
	 * Tests creating employee to database
	 */
	@Test
	@DisplayName("Create employee to DB")
	void createEmployee(){
		Employee emp = new Employee("Kalle Kuuma");
		controller.createEmployee(emp);
		List<Employee> empList = controller.readAllEmployees();
		assertTrue(empList.contains(emp),"Database should have the Employee!");
		controller.deleteEmployee(emp);
	}
	
	/**
	 * Tests updating employee information
	 */
	@Test
	@DisplayName("Update employee info")
	void updateEmployee() {
		Employee emp = new Employee("Kalle Kylma");
		controller.createEmployee(emp);
		int id = emp.getEmployeeID();
		emp.setName("Kimmo");
		controller.updateEmployee(emp);
		assertEquals("Kimmo", controller.readEmployee(id).getName());
		controller.deleteEmployee(emp);
	}
	
	/**
	 * Tests toString method
	 */
	@Test
	@DisplayName("toString test")
	void toStringTest() {
		boolean contains = testEmployee.toString().contains("Ukko");
		assertTrue(contains, "toString method is not working properly.");
	}
	
}
