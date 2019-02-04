package model;

import javax.persistence.*;
//kk

@Entity
@Table(name="drivingshifts")
public class DrivingShift {
	
	@Id
	@Column(name="shiftid")
	private int shiftID;
	
	@Column(name="starttime")
	private String startTime;
	
	@Column(name="finishtime")
	private String finishTime;
	
	@Column(name="shiftdriver")
	private Driver shiftDriver;
	
	@Column(name="shiftdriven")
	private boolean shiftDriven;

	public DrivingShift(int shiftID, String startTime, String finishTime, Driver shiftDriver) {
		this.shiftID = shiftID;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.shiftDriver = shiftDriver;
		this.shiftDriven = false;
	}
	
	

}
