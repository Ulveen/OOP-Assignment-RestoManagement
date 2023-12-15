package views;

import employee.Employee;

public class ManageMenus extends View {

	private Employee employee = null;
	
	public ManageMenus(Employee employee) {
		this.employee = employee;
		
	}

	@Override
	public void show() {
		int choice = -1;
		do {
			System.out.println("1. Create Menu");
			System.out.println("2. Delete Menu");
			System.out.println("3. Update Menu");
			System.out.println("4. Back");
			System.out.print(">> ");
			choice = inputHelper.getInteger();
			switch (choice) {
			case 1:
				
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:
				break;
			default:
				messageHelper.showMessage("Invalid input!");
				break;
			}
		} while(choice != 4);
	}

}
