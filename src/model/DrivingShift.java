package model;

import javax.persistence.*;
//kk

@Entity
@Table(name="Drivingshifts")
public class DrivingShift implements IDrivingShift{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="shiftid")
	private int shiftID;
	
	@Column(name="starttime")
	private String startTime;
	
	@Column(name="finishtime")
	private String finishTime;
	
	@Column(name="shiftdriver")
	private int shiftDriverID;
	
	@Column(name="cargoid")
	private int cargoID;
	
	@Column(name="clientid")
	private int clientID;
	
	@Column(name="vehicleid")
	private int vehicleID;
	
	@Column(name="shiftdriven")
	private boolean shiftDriven;
	
	@Column(name="shifttaken")
	private boolean shiftTaken;
	
	@Transient
	private IVehicle vehicle;
	
	@Transient
	private IDriver shiftDriver;
	
	@Transient
	private ICargo cargo;
	
	@Transient
	private IClient client;
	
	@Transient
	private Driver driver;

	/**
	 * Constructor for driving shift
	 * @param client client of the shift
	 * @param cargo cargo of the shift
	 */
	public DrivingShift(IClient client, ICargo cargo) {
		this.cargo = cargo;
		this.cargoID = cargo.getCargoID();
		this.client = client;
		this.clientID = client.getClientID();
		this.shiftTaken = false;
		this.shiftDriven = false;
	}
	
	/**
	 * empty constructor for the driving shift
	 */
	public DrivingShift() {
		
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

	public IDriver getShiftDriver() {
		return shiftDriver;
	}

	public void setShiftDriver(Driver shiftDriver) {
		this.shiftDriver = shiftDriver;
		this.shiftDriverID = shiftDriver.getEmployeeID();
	}

	public boolean isShiftDriven() {
		return shiftDriven;
	}

	public void setShiftDriven(boolean shiftDriven) {
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
	
	@Override
	public String toString() {
		return this.cargo + " " + this.client;
	}

}
