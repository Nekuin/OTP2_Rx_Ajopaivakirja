package client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Client;
import model.IClient;

public class ClientTest {
	
	static IClient testClient;
	
	@BeforeEach
	void resetClient() {
		testClient = new Client("Hookoo");
	}
	
	@Test
	@DisplayName("Test clientID")
	void setClientID() {
		testClient.setClientID(999);
		assertEquals(999, testClient.getClientID(), "Client ID not changed.");
	}
	
	@Test
	@DisplayName("Test getName")
	void getName() {
		assertEquals("Hookoo", testClient.getName(), "Client name not correct.");
	}
	
	@Test
	@DisplayName("Test setName")
	void setName() {
		testClient.setName("Testiasiakas");
		assertEquals("Testiasiakas", testClient.getName(), "Client name not changed.");
	}
}
