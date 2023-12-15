package views;

import employee.EmployeeRepository;
import restaurant.RestaurantBranch;

public class Register extends View {

	private final EmployeeRepository employeeRepository = EmployeeRepository.getInstance();
	
	public Register() {
		show();
	}

	@Override
	public void show() {
		String name = "";
		String branch = "";
		
		while(true) {
			System.out.print("Input your name: ");
			name = inputHelper.getString();
			if(name.isEmpty()) {
				messageHelper.showMessage("Name must not be empty!");
				continue;
			}
			break;
		}
		
		while(true) {
			System.out.print("Input restaurant branch: ");
			branch = inputHelper.getString();
			try {
				RestaurantBranch.valueOf(branch);
			} catch (Exception e) {
				messageHelper.showMessage(String.format("Restaurant Branch must be in (%s)", RestaurantBranch.getAllBranches(", ")));
				continue;
			}
			break;
		}
		
		final String employeeId = employeeRepository.createEmployee(name, branch);
		if(employeeId.isEmpty()) {
			messageHelper.showMessage("Error when inserting to database");
			return;
		} else {
			messageHelper.showMessage("Successfully registered a new account with ID: " + employeeId);
		}	
	}

}
