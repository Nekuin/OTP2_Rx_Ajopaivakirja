package model;

public interface ICargo {
	/**
	 * Returns the hazardousness of cargo
	 * @return
	 */
	public boolean isHazardous();
	/**
	 * Sets the hazardousness of cargo
	 * @param hazardous
	 */
	public void setHazardous(boolean hazardous);
	
	/**
	 * Returns weight of the cargo
	 * @return
	 */
	public double getWeight();
	
	/**
	 * Sets the weight of cargo
	 * @param weight
	 */
	public void setWeight(double weight);
	
	/**
	 * Returns the id of cargo
	 * @return
	 */
	public int getCargoID();
	
	/**
	 * Sets id of the cargo
	 * @param cargoID
	 */
	public void setCargoID(int cargoID);
	
	public void setShift(IDrivingShift shift);
	
	public IDrivingShift getShift();
	

}
