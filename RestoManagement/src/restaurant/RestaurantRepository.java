package restaurant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import database.Database;

public class RestaurantRepository {
	
	private final Database database = Database.getConnection();
	private static RestaurantRepository restaurantRepository = null;
	
	private RestaurantRepository() {
		
	}
	
	public static RestaurantRepository getInstance() {
		if(restaurantRepository == null ) {
			restaurantRepository = new RestaurantRepository();
		}
		return restaurantRepository;
	}
	
	public Restaurant castToRestaurant(ResultSet resultSet) {
		Restaurant restaurant = null;
		try {
			int restaurantId = resultSet.getInt(1);
			String restaurantName = resultSet.getString(2);
			RestaurantBranch restaurantBranch = RestaurantBranch.valueOf(resultSet.getString(3));
			restaurant = new Restaurant(restaurantId, restaurantName, restaurantBranch);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return restaurant;
	}
	
	public ArrayList<Restaurant> getAllRestaurants() {
		ArrayList<Restaurant> restaurants = new ArrayList<>();
		PreparedStatement selectFromRestaurants = database.prepareStatement(
			"SELECT restaurantId, restaurantName, restaurantBranch FROM MsRestaurant"
		);
		try {
			ResultSet resultSet = selectFromRestaurants.executeQuery();
			while(resultSet.next()) {
				Restaurant restaurant = castToRestaurant(resultSet);
				restaurants.add(restaurant);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return restaurants;
	}
	
}
