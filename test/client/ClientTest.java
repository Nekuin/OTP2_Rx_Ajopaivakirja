package client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.IController;
import model.Cargo;
import model.Client;

/**
 * Tests for client objects
 *
 */
public class ClientTest {
	
	static Client testClient;
	static IController controller;
	
	@BeforeAll
	static void init() {
		//use test version of controller by passing true as the last argument
		controller = new Controller(null,true);
	}
	
	/**
	 * Creates a client object
	 */
	@BeforeEach
	void resetClient() {
		testClient = new Client("Hookoo");
	}
	/**
	 * Tests getter and setter of client ID
	 */
	@Test
	@DisplayName("Test clientID")
	void setClientID() {
		testClient.setClientID(999);
		assertEquals(999, testClient.getClientID(), "Client ID not changed.");
	}
	
	/**
	 * Tests getter of client name
	 */
	@Test
	@DisplayName("Test getName")
	void getName() {
		assertEquals("Hookoo", testClient.getName(), "Client name not correct.");
	}
	/**
	 * Tests getter and setter of client name
	 */
	@Test
	@DisplayName("Test setName")
	void setName() {
		testClient.setName("Testiasiakas");
		assertEquals("Testiasiakas", testClient.getName(), "Client name not changed.");
	}
	
	/**
	 * Tests if we can create a client object, store it in the database
	 * and retrieve it
	 */
	@Test
	@DisplayName("Testing create to database")
	void createClient() {
		//create a new client
		Client client = new Client("MakenMakkara");
		controller.createClient(client);
		List<Client> clientList = controller.readAllClients();
		assertTrue(clientList.contains(client),"Database should have the client!");
	}
}
