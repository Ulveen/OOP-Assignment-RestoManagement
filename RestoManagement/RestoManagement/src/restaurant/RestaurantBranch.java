package restaurant;

public enum RestaurantBranch {

	Bandung,
	Jakarta,
	Bali,
	Surabaya,
	Samarinda,
	Padang;
	
	public static String getAllBranches(String sep) {
		String branch = "";
		for (RestaurantBranch restaurantBranch: values()) {
			branch += String.format("%s%s", sep, restaurantBranch);
		}
		return branch.substring(sep.length());
	}
	
}
