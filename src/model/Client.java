package model;

public class Client {
	private String name;
	private int clientID;
	
	
	public Client(String name, int clientID) {
		this.name = name;
		this.clientID = clientID;
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
	
	
	
	

}
