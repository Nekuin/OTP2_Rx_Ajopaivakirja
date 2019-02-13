package model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee implements IEmployee{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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


	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int getEmployeeID() {
		return employeeID;
	}
	
	@Override
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	
	@Override
	public String toString() {
		return "Employee: " + this.name + ", id: " + this.employeeID;
	}
	
}
