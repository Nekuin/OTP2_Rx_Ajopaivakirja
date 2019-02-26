package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

@Entity
@Table(name="Drivingshifts")
public class DrivingShift {
	
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
	/*
	@Column(name="cargoid")
	private int cargoID;
	*/
	@Column(name="clientid")
	private int clientID;
	
	@Column(name="vehicleid")
	private int vehicleID;
	
	@Column(name="shiftdriven")
	private boolean shiftDriven;
	
	@Column(name="shifttaken")
	private boolean shiftTaken;
	
	@Transient
	private Vehicle vehicle;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	//order column prevents MultipleBagsException
	@OrderColumn(name="ORDER_COL")
	private List<Cargo> cargo;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Client client;

	@ManyToOne(cascade = CascadeType.ALL)
	private Driver driver;
	

	/**
	 * Constructor for driving shift
	 * @param client client of the shift
	 * @param cargo cargo of the shift
	 */
	public DrivingShift(Client client, Cargo cargo) {
		this.cargo = new ArrayList<>();
		this.cargo.add(cargo);
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

	public Driver getShiftDriver() {
		return this.driver;
	}

	public void setShiftDriver(Driver shiftDriver) {
		this.driver = shiftDriver;
		this.shiftDriverID = shiftDriver.getEmployeeID();
	}

	public boolean isShiftDriven() {
		return shiftDriven;
	}

	public void setShiftDriven(boolean shiftDriven) {
		this.shiftDriven = true;
	}
	
	public void setVehicle(Vehicle vehicle) {
		this.vehicleID = vehicle.getCarID();
		this.vehicle = vehicle;
	}
	
	public void addCargo(Cargo cargo) {
		this.cargo.add(cargo);
	}
	
	public void setClient(Client client) {
		this.clientID = client.getClientID();
		this.client = client;
	}
	
	public Client getClient() {
		return this.client;
	}
	
	public List<Cargo> getCargo() {
		return this.cargo;
	}
	
	public String toString() {
		return "Shift id: " + this.shiftID + " " + this.cargo + " " + this.client;
	}

	public void setShiftTaken(boolean bool) {
		this.shiftTaken = bool;
	}

	public boolean getShiftTaken() {
		return this.shiftTaken;
	}

}
