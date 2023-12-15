package views;

import java.util.ArrayList;

import menu.LocalMenu;
import menu.Menu;
import menu.MenuCategory;
import menu.MenuRepository;
import menu.SpecialMenu;
import reservation.ReservationRepository;
import restaurant.Restaurant;

public class ManageMenus extends View {

	private final MenuRepository menuRepository = MenuRepository.getInstance();
	private final ReservationRepository reservationRepository = ReservationRepository.getInstance();
	private final Restaurant restaurant;
	
	public ManageMenus(Restaurant restaurant) {
		this.restaurant = restaurant;
		show();
	}

	@Override
	public void show() {
		int choice = -1;
		do {
			System.out.println("Manage Menus");
			System.out.println("1. Create Menu");
			System.out.println("2. Delete Menu");
			System.out.println("3. Update Menu");
			System.out.println("4. Back");
			choice = inputHelper.getInteger(">>");
			switch (choice) {
			case 1:
				createMenu();
				break;
			case 2:
				deleteMenu();
				break;
			case 3:
				updateMenu();
				break;
			case 4:
				break;
			default:
				messageHelper.showMessage("Invalid input!");
				break;
			}
		} while(choice != 4);
	}
	
	private void createMenu() {
		MenuCategory menuCategory = restaurant.getRestaurantBranch().getMenuCategory();
		
		int choice = 0;
		
		do {
			System.out.println("What kind of menu would you like to make?");
			System.out.println("1. Regular");
			System.out.println("2. " + menuCategory);
			choice = inputHelper.getInteger(">>");
			switch (choice) {
			case 1:
				menuCategory = MenuCategory.Regular;
				break;
			case 2:
				break;
			default:
				messageHelper.showMessage("Invalid input!");
				break;
			}
		} while(choice < 1 || choice > 2);
		
		String menuName = "";
		long price = -1;
		
		do {
			menuName = inputHelper.getString("Input menu name:");
			if(menuName.isBlank()) {
				messageHelper.showMessage("Menu name must not be empty");
				continue;
			}
			break;
		} while(true);
		
		do {
			price = inputHelper.getLong("Input menu price:");
			if(price <= 0) {
				messageHelper.showMessage("Menu price must be more than 0");
				continue;
			}
			break;
		} while(true);
		
		int result = 0;
		
		switch (menuCategory) {
		case Special:
			String story = "";
			do {
				story = inputHelper.getString("Input menu story:");
				if(story.isBlank()) {
					messageHelper.showMessage("Menu story must not be empty");
					continue;
				}
				break;
			} while(true);
			result = menuRepository.addSpecialMenu(restaurant.getRestaurantId(), menuCategory, menuName, price, story);
			break;
		case Local:
			String uniqueTraits = "";
			String origins = "";
			do {
				uniqueTraits = inputHelper.getString("Input menu unique trait:");
				if(uniqueTraits.isBlank()) {
					messageHelper.showMessage("Unique traits must not be empty");
					continue;
				}
				break;
			} while(true);
			do {
				origins = inputHelper.getString("Input menu origins:");
				if(origins.isBlank()) {
					messageHelper.showMessage("Origins must not be empty");
					continue;
				}
				break;
			} while(true);
			result = menuRepository.addLocalMenu(restaurant.getRestaurantId(), menuCategory, menuName, price, menuName, menuName);
			break;
		default:
			result = menuRepository.addMenu(restaurant.getRestaurantId(), menuCategory, menuName, price);
			break;
		}
		
		if(result > 0) {
			messageHelper.showMessage(String.format("Successfully added new menu %s Rp. %d", menuName, price));
			return;
		}
		
		messageHelper.showMessage("An unknown error occured when adding new menu");
				
	}
	
	private void deleteMenu() {
		ArrayList<Menu> menus = menuRepository.getAllMenu(restaurant.getRestaurantId());
		displayMenu(menus);
		if(menus.isEmpty()) return;
		Menu temp = null;
		do {
			int input = -1;
			input = inputHelper.getInteger("Input id to delete:");
			for(Menu menu : menus) {
				if(menu.getMenuId() == input) {
					temp = menu;
					break;
				}
			}
			if(temp == null) {
				messageHelper.showMessage("Please input a valid id!");
				continue;
			}
			break;
		} while(true);
		boolean isOrdered = reservationRepository.isMenuOrdered(temp.getMenuId());
		if(isOrdered) { 
			messageHelper.showMessage("Can't delete a menu that is already ordered");
			return;
		}
		int result = menuRepository.deleteMenu(temp.getMenuId());
		if(result == 0) {
			messageHelper.showMessage(String.format("Failed to delete %s", temp.getMenuName()));
			return;
		}
		messageHelper.showMessage(String.format("Succesfully deleted %s", temp.getMenuName()));
	}
	
	private void updateMenu() {
		ArrayList<Menu> menus = menuRepository.getAllMenu(restaurant.getRestaurantId());
		displayMenu(menus);
		if(menus.isEmpty()) return;
		Menu temp = null;
		
		do {
			int input = -1;
			input = inputHelper.getInteger("Input menu id to update:");
			for(Menu menu : menus) {
				if(menu.getMenuId() == input) {
					temp = menu;
					break;
				}
			}
			if(temp == null) {
				messageHelper.showMessage("Please input a valid id!");
				continue;
			}
			break;
		} while(true);
		
		boolean isOrdered = reservationRepository.isMenuOrdered(temp.getMenuId());
		
		if(isOrdered) { 
			messageHelper.showMessage("Can't update a menu that is already ordered");
			return;
		}
		
		String name = "";
		long price = -1;
		
		do {
			name = inputHelper.getString("Input updated name:");
			if(name.isBlank()) {
				System.out.println("Updated name must not be empty");
				continue;
			}
			break;
		} while(true);
		
		do {
			price = inputHelper.getLong("Input updated price:");
			if(price <= 0) {
				System.out.println("Updated price must be more than 0");
				continue;
			}
			break;
		} while(true);
		int result = 0;
		
		if(temp instanceof LocalMenu) {
			String uniqueness = "";
			String origins = "";
			do {
				uniqueness = inputHelper.getString("Input updated unique trait:");
				if(uniqueness.isBlank()) {
					messageHelper.showMessage("Updated unique trait must not be empty");
					continue;
				}
				break;
			} while (true);
			do {
				origins = inputHelper.getString("Input updated origins:");
				if(origins.isBlank()) {
					messageHelper.showMessage("Updated origins must not be empty");
					continue;
				}
				break;
			} while(true);
			LocalMenu a = (LocalMenu) temp;
			a.setUniqueness(uniqueness);
			a.setOrigin(origins);
			result = menuRepository.updateLocalMenu(a);
			if(result != 0) {
				messageHelper.showMessage(String.format("Successfully updated menu\nName: %s\nPrice: %d\nUnique Trait: %s\nOrigin: %s", 
					a.getMenuName(), a.getPrice(), a.getUniqueness(), a.getOrigin()));
				return;
			}
		} else if(temp instanceof SpecialMenu) {
			String story = "";
			do {
				story = inputHelper.getString("Input updated story:");
				if(story.isBlank()) {
					messageHelper.showMessage("Updated story must not be empty");
					continue;
				}
				break;
			} while(true);
			SpecialMenu a = (SpecialMenu) temp;
			a.setStory(story);
			result = menuRepository.updateSpecialMenu(a);
			if(result != 0) {
				messageHelper.showMessage(String.format("Successfully updated menu\nName: %s\nPrice: %d\nStory: %s", 
					a.getMenuName(), a.getPrice(), a.getStory()));				
				return;
			}
		} else {
			result = menuRepository.updateMenu(temp);
			if(result != 0) {
				messageHelper.showMessage(String.format("Successfully updated menu\nName: %s\nPrice: %d", 
					temp.getMenuName(), temp.getPrice()));
				return;
			}
		}
		messageHelper.showMessage("An unkown error occured when updating menu");
	}
	
	private void displayMenu(ArrayList<Menu> menus) {
		if(menus.isEmpty()) {
			messageHelper.showMessage("There are currently no menus available");
			return;
		}
		System.out.println("Menu List:");
		if(restaurant.getRestaurantBranch().getMenuCategory() == MenuCategory.Special) {
			messageHelper.printMulti("=", 68, true);
			System.out.printf("| %-3s | %-20s | %-12s | %-20s |\n",
					"Id", "Menu Name", "Price", "Story");
			messageHelper.printMulti("=", 68, true);
			for (Menu menu : menus) {
				if(menu instanceof SpecialMenu) {
					SpecialMenu temp = (SpecialMenu) menu;
					System.out.printf("| %-3s | %-20s | %-12s | %-20s |\n", 				
						menu.getMenuId(), 
						messageHelper.displayLimit(temp.getMenuName(), 20), 
						menu.getPrice(), 
						messageHelper.displayLimit(temp.getStory(), 20));
				} else {
					System.out.printf("| %-3s | %-20s | %-12s | %-20s |\n", 				
						menu.getMenuId(), 
						messageHelper.displayLimit(menu.getMenuName(), 20), 
						menu.getPrice(), "-");
				}
			}
			messageHelper.printMulti("=", 68, true);
		} else {
			messageHelper.printMulti("=", 91, true);
			System.out.printf("| %-3s | %-20s | %-12s | %-20s | %-20s |\n",
				"Id", "Menu Name", "Price", "Unique Traits", "Origin");
			messageHelper.printMulti("=", 91, true);
			for (Menu menu : menus) {
				if(menu instanceof LocalMenu) {
					LocalMenu temp = (LocalMenu) menu;
					System.out.printf("| %-3d | %-20s | Rp %-9d | %-20s | %-20s |\n", 				
						menu.getMenuId(),
						messageHelper.displayLimit(temp.getMenuName(), 20), menu.getPrice(), 
						messageHelper.displayLimit(temp.getUniqueness(), 20), 
						messageHelper.displayLimit(temp.getOrigin(), 20));
				} else {
					System.out.printf("| %-3d | %-20s | Rp %-9d | %-20s | %-20s |\n", 				
						menu.getMenuId(), 
						messageHelper.displayLimit(menu.getMenuName(), 20), 
						menu.getPrice(), "-", "-");
				}
			}
			messageHelper.printMulti("=", 91, true);
		}
	}

}
