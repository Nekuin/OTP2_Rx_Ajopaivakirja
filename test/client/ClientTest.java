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
import model.Client;
import model.Employee;
import util.TestUtil;

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
		controller = new Controller(null, TestUtil.testVersion);
	}
	
	/**
	 * Creates a client object
	 */
	@BeforeEach
	void resetClient() {
		testClient = new Client("Hookoo");
	}
	
	/**
	 * Testing the empty constructor
	 */
	@Test
	@DisplayName("Empty constructor test")
	void emptyContructorTest() {
		Client temp = new Client();
		boolean test = false;
		if (temp != null) {
			test = true;
		}
		assertEquals(true, test, "Empty client was not created.");
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
		controller.deleteClient(client);
	}
	
	@Test
	@DisplayName("Update to database")
	void updateClient() {
		Client client = new Client("Ranen Rengas");
		controller.createClient(client);
		int id = client.getClientID();
		client.setName("Ranen Renkaat");
		controller.updateClient(client);
		assertEquals("Ranen Renkaat", controller.readClient(id).getName());
		controller.deleteClient(client);
	}
	
	@Test
	@DisplayName("Test toString")
	void testToString() {
		Client client = new Client("Asiakas");
		boolean contains = client.toString().contains("Asiakas");
		assertTrue(contains, "toString method not working properly.");
	}
}
