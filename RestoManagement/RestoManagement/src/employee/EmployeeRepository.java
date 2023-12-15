package employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.Database;
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
	
	private Employee castToEmployee(ResultSet resultSet) {
		Employee employee = null;
		try {
			String employeeId = resultSet.getString(0);
			String employeeName = resultSet.getString(1);
			RestaurantBranch restaurantBranch = RestaurantBranch.valueOf(resultSet.getString(2));
			employee = new Employee(employeeId, employeeName, restaurantBranch);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}
	
	public Employee getEmployee(String id) {
		Employee employee = null;
		PreparedStatement selectEmployee = database.prepareStatement(
			"SELECT EmployeeID, EmployeeName, RestaurantBranch FROM MsEmployee emp JOIN MsRestaurant res ON emp.RestaurantID = res.RestaurantID WHERE emp.EmployeeID = ? LIMIT 1"
		);
		try {
			selectEmployee.setString(0, id);
			ResultSet resultSet = selectEmployee.executeQuery();
			employee = castToEmployee(resultSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}
	
	public String createEmployee(String name, String restaurantBranch) {
		String employeeId = "";
		PreparedStatement insertIntoEmployee = database.prepareStatement(
			"INSERT INTO MsEmployee VALUES (?, ?, ?)"
		);
		try {
			ResultSet selectLastEmployee = database.executeQuery("SELECT EmployeeID FROM MsEmployee ORDER BY EmployeeID DESC LIMIT 1");
			if(!selectLastEmployee.next()) {
				return employeeId;
			} else {
				employeeId = String.format("EMP%03d", Integer.parseInt(selectLastEmployee.getString(0).substring(3)) + 1);
				insertIntoEmployee.setString(0, employeeId);
				insertIntoEmployee.setString(1, name);
				insertIntoEmployee.setString(2, restaurantBranch);
				insertIntoEmployee.executeUpdate();
			}
		} catch (Exception e) {
			return employeeId;
		}
		return employeeId;
	}
	
}
