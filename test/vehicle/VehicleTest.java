package vehicle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.IVehicle;
import model.Vehicle;

public class VehicleTest {

	IVehicle testCar = new Vehicle("YKS-111", 0.0, 1000, "Sprinter", "Mercedes-Benz", 1001, true); 
	
	@Test
	@DisplayName("Test getModel()")
	void getModel() {	
		assertEquals("Sprinter", testCar.getModel(), "Car model not correct.");
	}
	
	@Test
	@DisplayName("Test setModel()")
	void setModel() {	
		testCar.setModel("GLC");
		assertEquals("GLC", testCar.getModel(), "Car model not changed.");
	}
	
	@Test
	@DisplayName("Test getBrand()")
	void getBrand() {	
		assertEquals("Mercedes-Benz", testCar.getBrand(), "Car brand not correct.");
	}
	
	@Test
	@DisplayName("Test setBrand()")
	void setBrand() {
		testCar.setBrand("Mersu");
		assertEquals("Mersu", testCar.getBrand(), "Car model not changed.");
	}
	
	@Test
	@DisplayName("Test getMaintained()")
	void getMaintained() {	
		assertEquals(true, testCar.getMaintained(), "Maintained false.");
	}
	
	@Test
	@DisplayName("Test setMaintained()")
	void setMaintained() {	
		testCar.setMaintained(false);
		assertEquals(false, testCar.getMaintained(), "Maintained not false.");
	}
	
	@Test
	@DisplayName("Test getRegNr()")
	void getRegNr() {	
		assertEquals("YKS-111", testCar.getRegNr(), "Register number not correct.");
	}
	
	@Test
	@DisplayName("Test setRegNr()")
	void setRegNr() {	
		testCar.setRegNr("KOO-333");
		assertEquals("KOO-333", testCar.getRegNr(), "Register number not changed.");
	}
	
	@Test
	@DisplayName("Test getDrivenDistance()")
	void getDrivenDistance() {	
		assertEquals(0.0, testCar.getDrivenDistance(), "Driven distance not correct.");
	}
	
	@Test
	@DisplayName("Test addDrivenDistance()")
	void addDrivenDistance() {	
		testCar.addDrivenDistance(10.0);
		assertEquals(10.0, testCar.getDrivenDistance(), "Driven distance not changed.");
	}
	
	@Test
	@DisplayName("Test getMaxCargoLoad()")
	void getMaxCargoLoad() {	
		assertEquals(1000, testCar.getMaxCargoLoad(), "Max cargo not correct.");
	}
	
	@Test
	@DisplayName("Test setMaxCargoLoad()")
	void setMaxCargoLoad() {
		testCar.setMaxCargoLoad(5000);
		assertEquals(5000, testCar.getMaxCargoLoad(), "Max cargo not correct.");
	}
	
	@Test
	@DisplayName("Test getCarID()")
	void getCarID() {
		assertEquals(1001, testCar.getCarID(), "Car ID not correct.");
	}
	
	@Test
	@DisplayName("Test setCarID()")
	void setCarID() {
		testCar.setCarID(2002);
		assertEquals(2002, testCar.getCarID(), "Car ID not changed.");
	}
	
}
