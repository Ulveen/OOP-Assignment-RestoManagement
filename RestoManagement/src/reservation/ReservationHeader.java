package reservation;

import java.util.ArrayList;

import table.TableType;

public class ReservationHeader {

	private int reservationId;
	private int restaurantId;
	private String employeeId;
	private String customerName;
	private int numberOfTable;
	private TableType tableType;
	private int numberOfPeople;
	private ReservationStatus reservationStatus;
	private ArrayList<ReservationDetail> reservationDetails;

	public ReservationHeader(int reservationId, int restaurantId, String employeeId, String customerName,
			int numberOfTable, TableType tableType, int numberOfPeople, ReservationStatus reservationStatus) {
		super();
		this.reservationId = reservationId;
		this.restaurantId = restaurantId;
		this.employeeId = employeeId;
		this.customerName = customerName;
		this.numberOfTable = numberOfTable;
		this.tableType = tableType;
		this.numberOfPeople = numberOfPeople;
		this.reservationStatus = reservationStatus;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getNumberOfTable() {
		return numberOfTable;
	}

	public void setNumberOfTable(int numberOfTable) {
		this.numberOfTable = numberOfTable;
	}

	public TableType getTableType() {
		return tableType;
	}

	public void setTableType(TableType tableType) {
		this.tableType = tableType;
	}

	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	public ReservationStatus getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(ReservationStatus reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	
	public ArrayList<ReservationDetail> getReservationDetails() {
		return reservationDetails;
	}

	public void setReservationDetails(ArrayList<ReservationDetail> reservationDetails) {
		this.reservationDetails = reservationDetails;
	}
	
}
