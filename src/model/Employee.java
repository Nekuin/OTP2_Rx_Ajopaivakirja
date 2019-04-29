package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

/**
 * Class for Employee
 * @author Nekuin
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="employeeID")
	private int employeeID;
	
	@Column(name="name")
	private String name;
	
	@Transient
	private String role;
	
	/**
	 * Constructor of the employee
	 * @param name name of the employee
	 */
	public Employee(String name) {
		this.name = name;
		role = "";
	}
	
	/**
	 * Get the role of this Employee
	 * @return
	 */
	public String getRole() {
		return role;
	}
	
	/**
	 * Set the role of this Employee
	 * @param role
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Empty constructor for the employee
	 */
	public Employee() {
	}


	/**
	 * Returns name of the employee
	 * @return the name of the Employee
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
	 * @return the ID of the Employee
	 */
	public int getEmployeeID() {
		return employeeID;
	}


	/**
	 * Returns a String representation of the Employee
	 */
	@Override
	public String toString() {
		return "Name: " + this.name + ", Role: " + this.role + ", Employee ID: " + this.employeeID;
	}
	
}
