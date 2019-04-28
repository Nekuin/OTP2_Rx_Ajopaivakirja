package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	
	@Column(name="deadline")
	private LocalDate deadline;
	
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
	 * Constructor for driving shift with initial cargo
	 * @param client Client client of the shift
	 * @param cargo Cargo cargo of the shift
	 * @param deadline LocalDate deadline for the shift
	 */
	public DrivingShift(Client client, Cargo cargo, LocalDate deadline) {
		this.deadline = deadline;
		this.cargo = new ArrayList<>();
		this.cargo.add(cargo);
		this.client = client;
		this.clientID = client.getClientID();
		this.shiftTaken = false;
		this.shiftDriven = false;
	}
	
	/**
	 * Constructor without initial cargo
	 * @param client Client client for the shift
	 * @param deadline LocalDate deadline for the shift
	 */
	public DrivingShift(Client client, LocalDate deadline) {
		this.deadline = deadline;
		this.cargo = new ArrayList<>();
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
	 * Setter for shift ID's
	 * @param shiftID
	 */
	public void setShiftID(int shiftID) {
		this.shiftID = shiftID;
	}
	
	/**
	 * Getter for shift ID's
	 * @return int
	 */
	public int getShiftID() {
		return shiftID;
	}
	
	/**
	 * Setter for deadlines
	 * @param deadline
	 */
	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}
	/**
	 * Getter for deadlines
	 * @return
	 */
	public LocalDate getDeadline() {
		return this.deadline;
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
	
	public Vehicle getVehicle() {
		return this.vehicle;
	}
	
	/**
	 * Adds cargo into a driving shift
	 * @param cargo cargo of the shift
	 */
	public void addCargo(Cargo cargo) {
		this.cargo.add(cargo);
	}
	
	/**
	 * Adds a list of cargo to this shift
	 * @param cargo List of Cargo objects
	 */
	public void addCargo(List<Cargo> cargo) {
		cargo.forEach(this::addCargo);
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
	@Override
	public String toString() {
		return "" + this.shiftID;
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
		return this.cargo.stream().mapToDouble(c -> c.getWeight()).sum();
	}

}
