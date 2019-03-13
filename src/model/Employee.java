package model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="employeeID")
	private int employeeID;
	
	@Column(name="name")
	private String name;
	
	/**
	 * Constructor of the employee
	 * @param name name of the employee
	 */
	public Employee(String name) {
		this.name = name;
	}

	/**
	 * Empty constructor for the employee
	 */
	public Employee() {
	}


	/**
	 * Returns name of the employee
	 * @return
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * Sets name of the employee
	 * @param name name of the employee
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * Returns id of the employee
	 * @return
	 */
	public int getEmployeeID() {
		return employeeID;
	}

	
	/**
	 * Sets id of the employee
	 * @param employeeID id of the employee
	 */
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}


	/**
	 * Returns information of the employee in string format
	 */
	@Override
	public String toString() {
		return "Employee: " + this.name + ", id: " + this.employeeID;
	}
	
}
