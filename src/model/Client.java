package model;

public class Client implements IClient {
	private String name;
	private int clientID;
	
	/**
	 * Constructor of the client
	 * @param name name of the client
	 * @param clientID id of the client
	 */
	public Client(String name, int clientID) {
		this.name = name;
		this.clientID = clientID;
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
	public int getClientID() {
		return clientID;
	}

	@Override
	public void setClientID(int clientID) {
		this.clientID = clientID;
	}
	
	
	
	

}
