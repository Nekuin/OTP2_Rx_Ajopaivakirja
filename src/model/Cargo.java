package model;

import javax.persistence.*;

@Entity
@Table(name="Cargo")
public class Cargo {
	
	@Id
	@Column(name="cargoID")
	private int cargoID;
	
	@Column(name="weight")
	private double weight;
	
	@Column(name="hazardous")
	private boolean hazardous;
	
	
	public Cargo(int cargoID, double weight, boolean hazardous) {
		this.cargoID = cargoID;
		this.weight = weight;
		this.hazardous = hazardous;
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

}
