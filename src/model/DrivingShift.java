package model;

import javax.persistence.*;
//kk

@Entity
@Table(name="Drivingshifts")
public class DrivingShift {
	
	@Id
	@Column(name="shiftid")
	private int shiftID;
	
	@Column(name="starttime")
	private String startTime;
	
	@Column(name="finishtime")
	private String finishTime;
	
	@Column(name="shiftdriver")
	private String shiftDriverName;
	
	@Column(name="cargoid")
	private int cargoID;
	
	@Column(name="clientid")
	private int clientID;
	
	@Column(name="vehicleid")
	private int vehicleID;
	
	@Column(name="shiftdriven")
	private boolean shiftDriven;
	
	@Transient
	private IVehicle vehicle;
	
	@Transient
	private Driver shiftDriver;
	
	@Transient
	private ICargo cargo;
	
	@Transient
	private IClient client;

	public DrivingShift(int shiftID, String startTime, String finishTime, Driver shiftDriver) {
		this.shiftID = shiftID;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.shiftDriver = shiftDriver;
		this.shiftDriven = false;
		this.shiftDriverName = shiftDriver.getName();
	}

	public int getShiftID() {
		return shiftID;
	}

	public void setShiftID(int shiftID) {
		this.shiftID = shiftID;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public Driver getShiftDriver() {
		return shiftDriver;
	}

	public void setShiftDriver(Driver shiftDriver) {
		this.shiftDriver = shiftDriver;
	}

	public boolean isShiftDriven() {
		return shiftDriven;
	}

	public void setShiftDriven() {
		this.shiftDriven = true;
	}
	
	public void setVehicle(IVehicle vehicle) {
		this.vehicleID = vehicle.getCarID();
		this.vehicle = vehicle;
	}
	
	public void setCargo(ICargo cargo) {
		this.cargoID = cargo.getCargoID();
		this.cargo = cargo;
	}
	
	public void setClient(IClient client) {
		this.clientID = client.getClientID();
		this.client = client;
	}
	
	

}
