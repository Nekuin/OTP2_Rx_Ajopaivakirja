package model;

import javax.persistence.*;

@Entity
@Table(name="Cargo")
public class Cargo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cargoID")
	private int cargoID;
	
	@Column(name="weight")
	private double weight;
	
	@Column(name="hazardous")
	private boolean hazardous;
	
	@ManyToOne
	private DrivingShift drivingShift;
	
	/**
	 * 
	 * @param weight weight of the cargo
	 * @param hazardous is the cargo hazardous
	 */
	public Cargo(double weight, boolean hazardous) {
		this.weight = weight;
		this.hazardous = hazardous;
	}
	
	
	/**
	 * Default constructor, hazardous is defaulted to false
	 * @param weight  weight of the cargo
	 */
	public Cargo(double weight) {
		this.weight = weight;
		this.hazardous = false;
	}
	
	
	/**
	 * Empty constructor for cargo
	 */
	public Cargo() {
		
	}
	
	public boolean isHazardous() {
		return hazardous;
	}

	public double getWeight() {
		return weight;
	}

	public int getCargoID() {
		return cargoID;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void setHazardous(boolean hazardous) {
		this.hazardous = hazardous;
	}
	
	public void setCargoID(int cargoID) {
		this.cargoID = cargoID;
	}
	
	public String toString() {
		return "Cargo ID: " + this.getCargoID() + " Weight: " + this.getWeight() + " Hazardous: " + this.isHazardous();
	}


	public void setShift(DrivingShift shift) {
		this.drivingShift = shift;
	}


	public DrivingShift getShift() {
		return this.drivingShift;
	}

}
