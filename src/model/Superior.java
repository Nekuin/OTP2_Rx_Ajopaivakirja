package model;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Class for superiors
 * @author tuoma
 *
 */
@Entity
public class Superior extends Employee{	
	
	
	/**
	 * Constructor with name
	 * @param name
	 */
	public Superior(String name) {
		super(name);
	}

	/**
	 * Empty constructor
	 */
	public Superior() {
		super();
	}
}
