package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

/**
 * 
 * @author tuoma
 * Class for driving shifts
 */
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
		this.cargo = new ArrayList<>();
	}
	
	/**
	 * Getter for shift ID's
	 * @return int
	 */
	public int getShiftID() {
		return shiftID;
	}
	
	/**
	 * Setter for shift ID's
	 * @param shiftID shifts ID
	 */
	public void setShiftID(int shiftID) {
		this.shiftID = shiftID;
	}

	/**
	 * Getter for starting time of a shift
	 * @return String
	 */
	public String getStartTime() {
		return startTime;
	}
	
	/**
	 * Setter for the starting time of a shift
	 * @param startTime starting time of the shift
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * Getter for the finishing time of a shift
	 * @return String
	 */
	public String getFinishTime() {
		return finishTime;
	}
	
	/**
	 * Setter for a finishing time of a shift
	 * @param finishTime finishing time of a shift
	 */
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	
	/**
	 * Getter for a driver of the shift
	 * @return Driver
	 */
	public Driver getShiftDriver() {
		return this.driver;
	}
	
	/**
	 * Setter for a driver of a shift
	 * @param shiftDriver driver of the shift
	 */
	public void setShiftDriver(Driver shiftDriver) {
		this.driver = shiftDriver;
	}
	
	/**
	 * Returns a boolean that tells whether the shift has been driven or not
	 * @return boolean is shift driven already
	 */
	public boolean isShiftDriven() {
		return shiftDriven;
	}
	
	/**
	 * Setter for the shiftDriven boolean
	 * @param shiftDriven is shift driven already
	 */
	public void setShiftDriven(boolean shiftDriven) {
		this.shiftDriven = true;
	}
	
	/**
	 * Setter for the vehicle of the shift
	 * @param vehicle vehicle that has been used for the shift
	 */
	public void setVehicle(Vehicle vehicle) {
		this.vehicleID = vehicle.getCarID();
		this.vehicle = vehicle;
	}
	
	/**
	 * Adds cargo into a driving shift
	 * @param cargo cargo of the shift
	 */
	public void addCargo(Cargo cargo) {
		this.cargo.add(cargo);
	}
	
	/**
	 * Sets client for a driving shift
	 * @param client client of the shift
	 */
	public void setClient(Client client) {
		this.clientID = client.getClientID();
		this.client = client;
	}
	
	/**
	 * Gets client of the shift
	 * @return Client
	 */
	public Client getClient() {
		return this.client;
	}
	
	/**
	 * Getter for the cargo list
	 * @return List<Cargo>
	 */
	public List<Cargo> getCargo() {
		return this.cargo;
	}
	
	/**
	 * returns a string representation of the driving shift
	 */
	public String toString() {
		//return "Shift id: " + this.shiftID + " " + this.cargo + " " + this.client;
		return "Shift id:  " + this.shiftID;
	}
	
	/**
	 * Setter for shiftTaken boolean, tells if someone has reserved the shift or not
	 * @param bool sets the boolean that tells whether the shift has been taken or not
	 */
	public void setShiftTaken(boolean bool) {
		this.shiftTaken = bool;
	}

	/**
	 * Getter for shiftTaken boolean, tells if someone has reserved the shift or not
	 * @return boolean
	 */
	public boolean getShiftTaken() {
		return this.shiftTaken;
	}
	
	/**
	 * Getter for the total weight of the cargo
	 * @return double
	 */
	public double getTotalCargoWeight() {
		return this.cargo.stream().mapToDouble(cargo -> cargo.getWeight()).sum();
	}

}
