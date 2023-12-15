package views;

import employee.Employee;
import restaurant.Restaurant;

public class ManageRestaurant extends View {

	private final Employee employee;
	private final Restaurant restaurant;
	
	public ManageRestaurant(Employee employee, Restaurant restaurant) {
		this.employee = employee;
		this.restaurant = restaurant;
		show();
	}

	@Override
	public void show() {
		int choice = -1;
		do {
			System.out.printf("Hello %s, %s\n", employee.getEmployeeName(), restaurant.getRestaurantBranch());
			System.out.println("1. Manage Menu");
			System.out.println("2. Manage Reservation");
			System.out.println("3. Logout");
			choice = inputHelper.getInteger(">>");
			switch (choice) {
			case 1:
				new ManageMenus(restaurant);
				break;
			case 2:
				new ManageReservations(employee, restaurant);
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
