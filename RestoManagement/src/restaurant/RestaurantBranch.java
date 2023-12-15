package restaurant;

import menu.MenuCategory;

public enum RestaurantBranch {

	Bandung(MenuCategory.Special),
	Jakarta(MenuCategory.Special),
	Bali(MenuCategory.Special),
	Surabaya(MenuCategory.Local),
	Samarinda(MenuCategory.Local),
	Padang(MenuCategory.Local);
	
	public static String getAllBranches(String sep) {
		String branch = "";
		for (RestaurantBranch restaurantBranch: values()) {
			branch += String.format("%s%s", sep, restaurantBranch);
		}
		return branch.substring(sep.length());
	}
	
	private final MenuCategory menuCategory;
	
	private RestaurantBranch(MenuCategory menuCategory) {
		this.menuCategory = menuCategory;
	}
	
	public MenuCategory getMenuCategory() {
		return this.menuCategory;
	}
	
}
