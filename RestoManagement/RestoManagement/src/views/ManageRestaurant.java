package views;

import employee.Employee;

public class ManageRestaurant extends View {

	private Employee employee = null;
	
	public ManageRestaurant(Employee employee) {
		this.employee = employee;
		
	}

	@Override
	public void show() {
		int choice = -1;
		do {
			System.out.println("1. Manage Menu");
			System.out.println("2. Manage Reservation");
			System.out.println("3. Logout");
			System.out.print(">> ");
			choice = inputHelper.getInteger();
			switch (choice) {
			case 1:
				new ManageMenus(employee);
				break;
			case 2:
				
				break;
			case 3:
				break;
			default:
				messageHelper.showMessage("Invalid Input!");
				break;
			}
		} while(choice != 3);
	}

}
