package restaurant;

public class Restaurant {

	private int restaurantId;
	private String restaurantName;
	private RestaurantBranch restaurantBranch;
	
	public Restaurant(int restaurantId, String restaurantName, RestaurantBranch restaurantBranch) {
		super();
		this.restaurantId = restaurantId;
		this.restaurantName = restaurantName;
		this.restaurantBranch = restaurantBranch;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public RestaurantBranch getRestaurantBranch() {
		return restaurantBranch;
	}

	public void setRestaurantBranch(RestaurantBranch restaurantBranch) {
		this.restaurantBranch = restaurantBranch;
	}
	
}
