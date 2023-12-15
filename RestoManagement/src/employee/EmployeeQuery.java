package employee;

public class EmployeeQuery {

	protected static final String GET_EMPLOYEE = "SELECT EmployeeID, EmployeeName, emp.RestaurantID, RestaurantBranch, RestaurantName FROM MsEmployee emp JOIN MsRestaurant res ON emp.RestaurantID = res.RestaurantID WHERE emp.EmployeeID = ? LIMIT 1";
	protected static final String GET_LAST_EMPLOYEE_ID = "SELECT EmployeeID FROM MsEmployee ORDER BY EmployeeID DESC LIMIT 1";
	protected static final String ADD_EMPLOYEE = "INSERT INTO MsEmployee VALUES (?, ?, ?)";
	
}
