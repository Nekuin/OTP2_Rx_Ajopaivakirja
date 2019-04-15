package model;
import javax.persistence.Entity;

/**
 * Class for the HrManager
 * @author Nekuin, Jori
 *
 */
@Entity
public class HrManager extends Employee{
	
	/**
	 * Constructor for the hr manager which takes the name of the HrManager as parameter
	 * @param name name of the hr manager
	 */
	public HrManager(String name) {
		super(name);
	}
	
	
	/**
	 * Empty constructor for HrManager
	 */
	public HrManager() {
		super();
	}

}
