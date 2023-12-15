package views;

import employee.Employee;

public class ManageReservations extends View {

	private Employee employee = null;
	
	public ManageReservations(Employee employee) {
		this.employee = employee;
		
	}

	@Override
	public void show() {
		int choice = -1;
		do {
			System.out.println("1. Add Reservation");
			System.out.println("2. Update Reservation");
			choice = inputHelper.getInteger();
			switch (choice) {
			case 1:
				
				break;
			case 2:
				
				break;
			case 3:
				break;
			default:
				messageHelper.showMessage("Invalid input!");
				break;
			}
		} while(choice != 3);
	}

}
