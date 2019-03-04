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
	
	/**
	 * Method that tells whether the cargo is hazardous or not
	 * @return returns true if cargo is hazardous and false if it is not
	 */
	public boolean isHazardous() {
		return hazardous;
	}

	/**
	 * Returns weight of the cargo
	 * @return
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * Returns the id of the cargo
	 * @return
	 */
	public int getCargoID() {
		return cargoID;
	}
	
	/**
	 * Method for setting the value of weight of the cargo
	 * @param weight weight of the cargo
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * Method for setting the hazardousness of the cargo
	 * @param hazardous hazardousness of the cargo
	 */
	public void setHazardous(boolean hazardous) {
		this.hazardous = hazardous;
	}
	
	/**
	 * Method for setting the cargo id
	 * @param cargoID id of the cargo
	 */
	public void setCargoID(int cargoID) {
		this.cargoID = cargoID;
	}
	
	/**
	 * Method for getting information about the cargo in string format
	 */
	public String toString() {
		return "Cargo ID: " + this.getCargoID() + " Weight: " + this.getWeight() + " Hazardous: " + this.isHazardous();
	}

/**
 * Method for setting the shift for cargo
 * @param shift shift that the cargo belongs to
 */
	public void setShift(DrivingShift shift) {
		this.drivingShift = shift;
	}

/**
 * Method for getting the shift of the cargo
 * @return
 */
	public DrivingShift getShift() {
		return this.drivingShift;
	}

}
