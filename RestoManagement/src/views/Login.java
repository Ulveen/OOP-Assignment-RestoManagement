package views;

import java.util.ArrayList;

import employee.Employee;
import employee.EmployeeRepository;
import restaurant.Restaurant;

public class Login extends View {

	private EmployeeRepository employeeRepository = EmployeeRepository.getInstance();
	private Employee employee = null;
	private Restaurant restaurant = null;
	
	public Login() {
		show();
	}

	@Override
	public void show() {
		String id = inputHelper.getString("Input your id:");
		if(!id.matches("^EMP[0-9]{3}$")) {
			messageHelper.showMessage("Invalid Id");
			return;
		}
		ArrayList<Object> temp = employeeRepository.getEmployee(id);
		if(temp == null) {
			messageHelper.showMessage("There are no employee with ID " + id);
			return;
		}
		employee = (Employee) temp.get(0);
		restaurant = (Restaurant) temp.get(1);
		new ManageRestaurant(employee, restaurant);
	}

}
