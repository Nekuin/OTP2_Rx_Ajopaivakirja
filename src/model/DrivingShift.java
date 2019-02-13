package model;

import javax.persistence.*;
//kk

@Entity
@Table(name="Drivingshifts")
public class DrivingShift {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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
	private IDriver shiftDriver;
	
	@Transient
	private ICargo cargo;
	
	@Transient
	private IClient client;

	/**
	 * 
	 * @param shiftID id for the shift
	 * @param startTime starting time of the shift
	 * @param finishTime time the shift is to be finished
	 * @param shiftDriver driver of the shift
	 */
	public DrivingShift(String startTime, String finishTime, IDriver shiftDriver) {
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.shiftDriver = shiftDriver;
		this.shiftDriven = false;
		this.shiftDriverName = shiftDriver.getName();
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
		return this.startTime + "-" + this.finishTime + ", driver: " + this.shiftDriverName;
	}

}
