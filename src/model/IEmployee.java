package model;

public interface IEmployee {
	
	/**
	 * Returns name of the employee
	 * @return
	 */
	public String getName();
	
	/**
	 * Sets the name of the employee
	 * @param name
	 */
	public void setName(String name);
	
	/**
	 * Returns the id of the employee
	 * @return
	 */
	public int getEmployeeID();
	
	/**
	 * Sets the id of the employee
	 * @param employeeID
	 */
	public void setEmployeeID(int employeeID);

}
