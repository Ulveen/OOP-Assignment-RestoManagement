package reservation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import database.Database;
import table.TableType;

public class ReservationRepository {

	private static ReservationRepository reservationRepository;
	private final Database database = Database.getConnection();;
	
	private ReservationRepository() {
		
	}
	
	public static ReservationRepository getInstance() {
		if(reservationRepository == null) reservationRepository = new ReservationRepository();
		return reservationRepository;
	}
	
	public boolean isMenuOrdered(int menuId) {
		try {
			PreparedStatement getMenuCount = database.prepareStatement(ReservationQuery.GET_MENU_COUNT);
			getMenuCount.setInt(1, menuId);
			ResultSet resultSet = getMenuCount.executeQuery();
			if(!resultSet.next()) return false;
			return resultSet.getInt(1) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private ReservationHeader castToReservationHeader(ResultSet resultSet) {
		try {
			int reservationId = resultSet.getInt(1);
			int restaurantId = resultSet.getInt(2);
			String employeeId = resultSet.getString(3);
			String customerName = resultSet.getString(4); 
			int numberOfTable = resultSet.getInt(5);
			TableType tableType = TableType.valueOf(resultSet.getString(6));
			int numberOfPeople = resultSet.getInt(7);
			ReservationStatus reservationStatus = ReservationStatus.valueOf(resultSet.getString(8)) ;
			return new ReservationHeader(reservationId, restaurantId, employeeId, customerName, numberOfTable, tableType, numberOfPeople, reservationStatus);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private ReservationDetail castToReservationDetail(ResultSet resultSet) {
		try {
			int reservationId = resultSet.getInt(1);
			String menuName = resultSet.getString(2);
			long price = resultSet.getLong(3);
			int quantity = resultSet.getInt(4);
			ReservationDetail rd = new ReservationDetail(reservationId, quantity);
			rd.setPrice(price);
			rd.setMenuName(menuName);
			return rd;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<ReservationHeader> getReservationByStatus(ReservationStatus reservationStatus){
		ArrayList<ReservationHeader> reservationHeaders = new ArrayList<>();
		try {
			PreparedStatement getReservationByStatus = database.prepareStatement(ReservationQuery.GET_RESERVATIONS_BY_STATUS);
			getReservationByStatus.setString(1, reservationStatus.toString());
			ResultSet resultSet = getReservationByStatus.executeQuery();
			while(resultSet.next()) {
				reservationHeaders.add(castToReservationHeader(resultSet));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return reservationHeaders;
	}
	
	public int addReservation(int restaurantId, String employeeId, String customerName, ReservationStatus reservationStatus, int numberOfTable, TableType tableType, int numberOfPeople, ArrayList<ReservationDetail> reservationDetails) {
		int temp = 0;
		try {
			PreparedStatement insertIntoReservationHeader = database.prepareStatement(ReservationQuery.ADD_RESERVATION_HEADER);
			insertIntoReservationHeader.setInt(1, restaurantId);
			insertIntoReservationHeader.setString(2, employeeId);
			insertIntoReservationHeader.setString(3, customerName);
			insertIntoReservationHeader.setInt(4, numberOfTable);
			insertIntoReservationHeader.setString(5, tableType.toString());
			insertIntoReservationHeader.setInt(6, numberOfPeople);
			insertIntoReservationHeader.setString(7, reservationStatus.toString());
			int result = insertIntoReservationHeader.executeUpdate();
			if(result == 0) return result;
			ResultSet resultSet = database.executeQuery(ReservationQuery.GET_LAST_RESERVATION_ID);
			if(!resultSet.next()) {
				return 0;
			}
			int reservationId = resultSet.getInt(1);
			temp = handleReservationDetails(reservationId, reservationDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	public int updateToInOrder(int reservationId, ArrayList<ReservationDetail> reservationDetails) {
		int result = handleReservationDetails(reservationId, reservationDetails);
		if(result == 0) return result;
		try {
			result = updateReservationStatus(ReservationStatus.InOrder, reservationId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateReservationStatus(ReservationStatus reservationStatus, int reservationId) {
		try {
			PreparedStatement updateReservationStatus = database.prepareStatement(ReservationQuery.UPDATE_RESERVATION_STATUS);
			updateReservationStatus.setString(1, reservationStatus.toString());
			updateReservationStatus.setInt(2, reservationId);
			return updateReservationStatus.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public int handleReservationDetails(int reservationId, ArrayList<ReservationDetail> reservationDetails) {
		for (ReservationDetail reservationDetail : reservationDetails) {
			int result = addReservationDetail(reservationId, reservationDetail);
			if(result == 0) return 0;
		}
		return 1;
	}
	
	public int addReservationDetail(int reservationId, ReservationDetail reservationDetail) {
		try {
			PreparedStatement insertIntoReservationDetail = database.prepareStatement(ReservationQuery.ADD_RESERVATION_DETAIL);
			insertIntoReservationDetail.setInt(1, reservationId);
			insertIntoReservationDetail.setInt(2, reservationDetail.getMenuID());
			insertIntoReservationDetail.setInt(3, reservationDetail.getQuantity());
			return insertIntoReservationDetail.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public ArrayList<ReservationDetail> getReservationDetail(int reservationId) {
		ArrayList<ReservationDetail> reservationDetails = new ArrayList<>();
		try {
			PreparedStatement selectReservationDetail = database.prepareStatement(ReservationQuery.GET_RESERVATION_DETAILS);
			selectReservationDetail.setInt(1, reservationId);
			ResultSet resultSet = selectReservationDetail.executeQuery();
			while(resultSet.next()) {
				reservationDetails.add(castToReservationDetail(resultSet));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return reservationDetails;
	}

}
