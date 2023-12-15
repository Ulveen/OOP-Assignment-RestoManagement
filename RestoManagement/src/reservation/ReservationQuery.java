package reservation;

public class ReservationQuery {

	public final static String GET_MENU_COUNT = "SELECT COUNT(*) AS count FROM ReservationDetail WHERE MenuID = ?";
	public final static String ADD_RESERVATION_HEADER = "INSERT INTO ReservationHeader(RestaurantID, EmployeeID, CustomerName, NumberOfTable, TableType, NumberOfPeople, ReservationStatus) VALUES (?, ?, ?, ?, ?, ?, ?)";
	public final static String ADD_RESERVATION_DETAIL = "INSERT INTO ReservationDetail(ReservationID, MenuID, Quantity) VALUES(?, ?, ?)";
	public final static String GET_LAST_RESERVATION_ID = "SELECT ReservationID FROM ReservationHeader ORDER BY ReservationID DESC LIMIT 1";
	public final static String GET_RESERVATIONS_BY_STATUS = "SELECT ReservationID, RestaurantID, EmployeeID, CustomerName, NumberOfTable, TableType, NumberOfPeople, ReservationStatus FROM reservationheader WHERE ReservationStatus = ?";
	public final static String UPDATE_RESERVATION_STATUS = "UPDATE ReservationHeader SET ReservationStatus = ? WHERE ReservationID = ?";
	public final static String GET_RESERVATION_DETAILS = "SELECT rd.ReservationID, menu.MenuName, menu.Price, rd.Quantity FROM Reservationdetail rd JOIN MsMenu menu ON rd.MenuID = menu.MenuID WHERE rd.ReservationID = ?";
	
}
