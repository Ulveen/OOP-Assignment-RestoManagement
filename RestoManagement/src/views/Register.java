package views;

import java.util.ArrayList;

import employee.EmployeeRepository;
import restaurant.Restaurant;
import restaurant.RestaurantRepository;

public class Register extends View {

	private final EmployeeRepository employeeRepository = EmployeeRepository.getInstance();
	private final RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();
	
	public Register() {
		show();
	}

	@Override
	public void show() {
		String name = "";
		int restaurantId = -1;
		
		do {
			name = inputHelper.getString("Input your name:");
			if(name.isEmpty()) {
				messageHelper.showMessage("Name must not be empty!");
				continue;
			}
			break;
		} while(true);
		
		ArrayList<Restaurant> restaurants = restaurantRepository.getAllRestaurants();
		if(restaurants.isEmpty()) {
			messageHelper.showMessage("There are currently no restaurants");
			return;
		}
		
		do {
			System.out.println("\nRestaurant List:");
			messageHelper.printMulti("=", 53, true);
			System.out.printf("| %-3s | %-25s | %-15s |\n", "Id", "Name", "Branch");
			messageHelper.printMulti("=", 53, true);
			for (Restaurant r : restaurants) {
				System.out.printf("| %-3d | %-25s | %-15s |\n", r.getRestaurantId(), r.getRestaurantName(), r.getRestaurantBranch().toString());
			}
			messageHelper.printMulti("=", 53, true);
			
			restaurantId = inputHelper.getInteger("Input restaurant id:");
			
			if(restaurantId < 1 || restaurantId > restaurants.size()) {
				messageHelper.showMessage("Restaurant id must be between 1 - " + restaurants.size());
				continue;
			}
			
			break;
		} while(true);
		
		String employeeId = employeeRepository.createEmployee(name, restaurantId);
		if(employeeId.isEmpty()) {
			messageHelper.showMessage("Error when inserting to database");
			return;
		}
		messageHelper.showMessage("Successfully registered a new account with ID: " + employeeId);	
	}

}
