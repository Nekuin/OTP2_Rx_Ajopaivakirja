package model;

public class Cargo {
	private double weight;
	private boolean hazardous;
	
	
	public Cargo(double weight, boolean hazardous) {
		this.weight = weight;
		this.hazardous = hazardous;
	}
	
	public boolean isHazardous() {
		return hazardous;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void setHazardous(boolean hazardous) {
		this.hazardous = hazardous;
	}
	
	

}
