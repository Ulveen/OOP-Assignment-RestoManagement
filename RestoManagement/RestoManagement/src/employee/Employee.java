package employee;

import restaurant.RestaurantBranch;

public class Employee {

	private String id;
	private String name;
	private RestaurantBranch restaurantBranch;
	
	protected Employee(String id, String name, RestaurantBranch restaurantBranch) {
		super();
		this.id = id;
		this.name = name;
		this.restaurantBranch = restaurantBranch;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RestaurantBranch getBranch() {
		return restaurantBranch;
	}

	public void setBranch(RestaurantBranch restaurantBranch) {
		this.restaurantBranch = restaurantBranch;
	}
	
}
