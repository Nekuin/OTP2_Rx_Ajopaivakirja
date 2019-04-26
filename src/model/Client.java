package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class for clients
 * @author tuoma
 *
 */
@Entity
@Table(name="Client")
public class Client {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "clientid")
	private int clientID;
	
	@Column(name="name")
	private String name;
	
	
	/**
	 * Constructor of the client
	 * @param name name of the client
	 * @param clientID id of the client
	 */
	public Client(String name) {
		this.name = name;
	}
	
	/**
	 * Empty constructor for client
	 */
	public Client() {
		
	}

	/**
	 * Method that returns the name of the client
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method for setting the name of the client
	 * @param name name of the client
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Method for getting the client's id
	 * @return
	 */
	public int getClientID() {
		return clientID;
	}

	/**
	 * Method fo setting the client's id
	 * @param clientID client's id
	 */
	public void setClientID(int clientID) {
		this.clientID = clientID;
	}
	
	/**
	 * Method for getting information about the client in string format
	 */
	@Override
	public String toString() {
		return this.name;
	}
	
	

}
