package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}
	
	public String toString() {
		return "Client ID: " + this.clientID + " Name: " + this.name;
	}
	
	

}
