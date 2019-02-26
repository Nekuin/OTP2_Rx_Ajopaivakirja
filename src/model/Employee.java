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


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	
	@Override
	public String toString() {
		return "Employee: " + this.name + ", id: " + this.employeeID;
	}
	
}
