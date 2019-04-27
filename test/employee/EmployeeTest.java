package employee;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Employee;

public class EmployeeTest {
	
	static Employee testEmployee;
	
	/**
	 * Creates a new employee before each test
	 */
	@BeforeEach
	void resetEmployee() {
		testEmployee = new Employee("Röi Ukko");
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
	 * Tests employee id setter
	 */
	@Test
	@DisplayName("Test employeeID")
	void setEmployeeID() {	
		testEmployee.setEmployeeID(2010);
		assertEquals(2010, testEmployee.getEmployeeID(), "Employee ID not changed.");
	}
}
