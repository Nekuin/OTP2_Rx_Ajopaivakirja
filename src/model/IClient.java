package model;

public interface IClient {
	/**
	 * Returns name of the client
	 * @return
	 */
	public String getName();
	
	/**
	 * Sets the name of the client
	 * @param name
	 */
	public void setName(String name);
	
	/**
	 * Returns the name of the client
	 * @return
	 */
	public int getClientID();
	
	/**
	 * Sets the name of the client
	 * @param clientID
	 */
	public void setClientID(int clientID);

}
