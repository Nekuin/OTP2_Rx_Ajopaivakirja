package employee;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Employee;
import model.IEmployee;

public class EmployeeTest {
	
	static IEmployee testEmployee;
	
	@BeforeEach
	void resetEmployee() {
		testEmployee = new Employee("Röi Ukko", 2001);
	}
	
	@Test
	@DisplayName("Test getName")
	void getName() {	
		assertEquals("Röi Ukko", testEmployee.getName(), "Employee name not correct.");
	}
	
	@Test
	@DisplayName("Test getName")
	void setName() {
		testEmployee.setName("Röi Akka");
		assertEquals("Röi Akka", testEmployee.getName(), "Employee name not changed.");
	}
	
	@Test
	@DisplayName("Test getEmployeeID")
	void getEmployeeID() {	
		assertEquals(2001, testEmployee.getEmployeeID(), "Employee ID not correct.");
	}
	
	@Test
	@DisplayName("Test setEmployeeID")
	void setEmployeeID() {	
		testEmployee.setEmployeeID(2010);
		assertEquals(2010, testEmployee.getEmployeeID(), "Employee ID not changed.");
	}
}
