package model;

import javax.persistence.*;

@Entity
@Table(name="Employees")
public class Employee {
	
	@Column(name="name")
	private String name;
	
	@Id
	@Column(name="employeeID")
	private int employeeID;
	
	public Employee(String name, int employeeID) {
		this.name = name;
		this.employeeID = employeeID;
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
	
}
