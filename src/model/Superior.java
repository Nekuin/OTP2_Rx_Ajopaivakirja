package model;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Class for superiors
 * @author tuoma
 *
 */
@Entity
public class Superior extends Employee{	
	
	@Transient
	private VehicleAccessObject vao;
	
	/**
	 * Constructor with name
	 * @param name
	 */
	public Superior(String name) {
		super(name);
	}

	/**
	 * Empty constructor
	 */
	public Superior() {
		super();
	}
	
	/**
	 * Setter for the data access object
	 * @param vao VehicleAccessObject
	 */
	public void setVehicleAccessObject( VehicleAccessObject vao) {
		this.vao = vao;
	}
	
	/**
	 * Method for creating a new vehicle
	 * @param regNr
	 * @param drivenDistance
	 * @param maxCargoLoad
	 * @param model
	 * @param brand
	 * @param maintained
	 */
	public void createVehicle(String regNr, double drivenDistance, int maxCargoLoad, String model, String brand, boolean maintained) {
		Vehicle newVehicle = new Vehicle(regNr, drivenDistance, maxCargoLoad, model, brand, maintained);
		vao.createVehicle(newVehicle);
	}
	
	/**
	 * Method for deleting a vehicle
	 * @param vehicle
	 */
	public void deleteVehicle(Vehicle vehicle) {
		vao.deleteVehicle(vehicle.getCarID());
	}
}
