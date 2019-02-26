package model;

public interface IDrivingShift {
	
	/**
	 * Returns shift id
	 * @return
	 */
	public int getShiftID();
	
	
	/**
	 * Sets shift id
	 * @param shiftID
	 */
	public void setShiftID(int shiftID);
	
	/**
	 * Returns the starting time of the shift
	 * @return
	 */
	public String getStartTime();
	
	/**
	 * Sets starting time of the shift
	 * @param startTime
	 */
	public void setStartTime(String startTime);
	
	/**
	 * Returns finish time of the shift
	 * @return
	 */
	public String getFinishTime();
	
	/**
	 * Sets finish time of the shift
	 * @param finishTime
	 */
	public void setFinishTime(String finishTime);
	
	/**
	 * Returns the driver of the shift
	 * @return
	 */
	public IDriver getShiftDriver();
	
	/**
	 * Returns if the shift is driven or not
	 * @return
	 */
	public boolean isShiftDriven();
	
	/**
	 * Set shift driven or not driven
	 * @param shiftDriven
	 */
	public void setShiftDriven(boolean shiftDriven);
	
	public void setShiftDriver(IDriver driver);
	
	public void setShiftTaken(boolean bool);
	
	public boolean getShiftTaken();
	

}
