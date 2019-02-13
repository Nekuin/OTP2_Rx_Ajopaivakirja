package model;

import javax.persistence.*;

@Entity
@Table(name="Cargo")
public class Cargo implements ICargo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cargoID")
	private int cargoID;
	
	@Column(name="weight")
	private double weight;
	
	@Column(name="hazardous")
	private boolean hazardous;
	
	/**
	 * 
	 * @param cargoID id of the cargo
	 * @param weight weight of the cargo
	 * @param hazardous is the cargo hazardous
	 */
	public Cargo(double weight, boolean hazardous) {
		this.weight = weight;
		this.hazardous = hazardous;
	}
	
	
	/**
	 * Default constructor, hazardous is set to false
	 * @param cargoID id of the cargo
	 * @param weight  weight of the cargo
	 */
	public Cargo(int cargoID, double weight) {
		this.cargoID = cargoID;
		this.weight = weight;
		this.hazardous = false;
	}
	
	
	/**
	 * Empty constructor for cargo
	 */
	public Cargo() {
		
	}
	
	
	
	@Override
	public boolean isHazardous() {
		return hazardous;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public int getCargoID() {
		return cargoID;
	}
	
	@Override
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public void setHazardous(boolean hazardous) {
		this.hazardous = hazardous;
	}
	
	@Override
	public void setCargoID(int cargoID) {
		this.cargoID = cargoID;
	}

}
