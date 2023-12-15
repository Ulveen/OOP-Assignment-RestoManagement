package employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import database.Database;
import restaurant.Restaurant;
import restaurant.RestaurantBranch;

public class EmployeeRepository {
	
	private final static Database database = Database.getConnection();
	private static EmployeeRepository employeeRepository = null;
	
	private EmployeeRepository() {
		
	}
	
	public synchronized static EmployeeRepository getInstance() {
		if(employeeRepository == null) {
			employeeRepository = new EmployeeRepository();
		}
		return employeeRepository;
	}
	
	private ArrayList<Object> castToRestaurantEmployee(ResultSet resultSet) {
		ArrayList<Object> temp = new ArrayList<>();
		try {
			if(!resultSet.next()) {
				return null;
			}
			String employeeId = resultSet.getString(1);
			String employeeName = resultSet.getString(2);
			
			Employee employee = new Employee(employeeId, employeeName);
			
			int restaurantId = resultSet.getInt(3);
			RestaurantBranch restaurantBranch = RestaurantBranch.valueOf(resultSet.getString(4));
			String restaurantName = resultSet.getString(5);
			
			Restaurant restaurant = new Restaurant(restaurantId, restaurantName, restaurantBranch);
			
			temp.add(employee);
			temp.add(restaurant);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	public ArrayList<Object> getEmployee(String id) {
		ArrayList<Object> temp = null;
		try {
			PreparedStatement selectEmployee = database.prepareStatement(EmployeeQuery.GET_EMPLOYEE);
			selectEmployee.setString(1, id);
			ResultSet resultSet = selectEmployee.executeQuery();
			temp = castToRestaurantEmployee(resultSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	public String createEmployee(String name, int restaurantId) {
		String employeeId = "";
		try {
			PreparedStatement insertIntoEmployee = database.prepareStatement(EmployeeQuery.ADD_EMPLOYEE);
			ResultSet selectLastEmployee = database.executeQuery(EmployeeQuery.GET_LAST_EMPLOYEE_ID);
			if(!selectLastEmployee.next()) {
				employeeId = "EMP001";
			} else {
				employeeId = String.format("EMP%03d", Integer.parseInt(selectLastEmployee.getString(1).substring(3)) + 1);
			}
			insertIntoEmployee.setString(1, employeeId);
			insertIntoEmployee.setString(2, name);
			insertIntoEmployee.setInt(3, restaurantId);
			insertIntoEmployee.executeUpdate();
		} catch (Exception e) {
			return "";
		}
		return employeeId;
	}
	
}
