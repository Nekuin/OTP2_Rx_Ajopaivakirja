package model;

import javax.persistence.*;

/**
 * Class for superiors
 * @author tuoma
 *
 */
@Entity
public class Superior extends Employee{	
	
	@Transient
	private VehicleAccessObject vao;
	
	public Superior(String name) {
		super(name);
	}

	public Superior() {
		super();
	}
	
	public void setVehicleAccessObject( VehicleAccessObject vao) {
		this.vao = vao;
	}
	
	public void createVehicle(String regNr, double drivenDistance, int maxCargoLoad, String model, String brand, boolean maintained) {
		Vehicle newVehicle = new Vehicle(regNr, drivenDistance, maxCargoLoad, model, brand, maintained);
		vao.createVehicle(newVehicle);
	}
	
	public void deleteVehicle(Vehicle vehicle) {
		vao.deleteVehicle(vehicle.getCarID());
	}
}
