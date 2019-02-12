package model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee implements IEmployee{
	
	@Id
	@Column(name="employeeID")
	private int employeeID;
	
	@Column(name="name")
	private String name;
	
	/**
	 * Constructor of the employee
	 * @param name name of the employee
	 * @param employeeID id of the employee
	 */
	public Employee(String name, int employeeID) {
		this.name = name;
		this.employeeID = employeeID;
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
	
}
