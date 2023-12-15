package views;

import employee.Employee;
import employee.EmployeeRepository;

public class Login extends View {

	private EmployeeRepository employeeRepository = EmployeeRepository.getInstance();
	private Employee employee = null;
	
	public Login() {
		show();
	}

	@Override
	public void show() {
		System.out.print("Input your id: ");
		String id = inputHelper.getString();
		employee = employeeRepository.getEmployee(id);
		if(employee == null) {
			messageHelper.showMessage("ID doesn't exist");
			return;
		}
		new ManageRestaurant(employee);
	}

}
