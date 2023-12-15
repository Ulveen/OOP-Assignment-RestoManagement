package views;

import java.util.ArrayList;

import employee.Employee;
import menu.LocalMenu;
import menu.Menu;
import menu.MenuCategory;
import menu.MenuRepository;
import menu.SpecialMenu;
import reservation.ReservationDetail;
import reservation.ReservationHeader;
import reservation.ReservationRepository;
import reservation.ReservationStatus;
import restaurant.Restaurant;
import table.TableType;

public class ManageReservations extends View {

	private final Employee employee;
	private final Restaurant restaurant;
	private final ReservationRepository reservationRepository = ReservationRepository.getInstance();
	private final MenuRepository menuRepository = MenuRepository.getInstance();
	
	public ManageReservations(Employee employee, Restaurant restaurant) {
		this.employee = employee;
		this.restaurant = restaurant;
		show();
	}

	@Override
	public void show() {
		int choice = -1;
		do {
			System.out.println("Manage Reservations");
			System.out.println("1. Add Reservation");
			System.out.println("2. Update Reservation");
			System.out.println("3. Checkout");
			choice = inputHelper.getInteger(">>");
			switch (choice) {
			case 1:
				createReservation();
				break;
			case 2:
				updateReservation();
				break;
			case 3:
				checkOut();
				break;
			case 4:
				break;
			default:
				messageHelper.showMessage("Invalid input!");
				break;
			}
		} while(choice != 4);
	}
	
	private void createReservation() {
		String customerName = "";
		ReservationStatus reservationStatus = ReservationStatus.InReserve;
		int numberOfTable = -1;
		TableType tableType = TableType.General;
		int numberOfPeople = -1;
		
		do {
			customerName = inputHelper.getString("Input customer name:");
			if(customerName.isBlank()) {
				messageHelper.showMessage("Customer name must not be empty");
				continue;
			}
			break;
		} while(true);
		
		do {
			int choice = -1;
			System.out.println("What kind of reservation would you like to make?");
			System.out.println("1. In reserve");
			System.out.println("2. In order");
			choice = inputHelper.getInteger(">>");
			if(choice < 1 || choice > 2) {
				messageHelper.showMessage("Invalid input!");
				continue;
			}
			if(choice == 2) reservationStatus = ReservationStatus.InOrder;
			break;
		} while(true);
		
		do {
			numberOfTable = inputHelper.getInteger("Input number of table:");
			if(numberOfTable <= 0) {
				messageHelper.showMessage("Number of table must be more than 0");
				continue;
			}
			break;
		} while(true);
		
		do {
			int choice = -1;
			System.out.println("What kind of table type would you like?");
			System.out.println("1. " + TableType.Romantic + " " + TableType.Romantic.getCapacity());
			System.out.println("2. " + TableType.General + " " + TableType.General.getCapacity());
			System.out.println("3. " + TableType.Family + " " + TableType.Family.getCapacity());
			choice = inputHelper.getInteger(">>");
			if(choice < 1 || choice > 3) {
				messageHelper.showMessage("Invalid input!");
				continue;
			}
			switch (choice) {
			case 1:
				tableType = TableType.Romantic;
				break;
			case 3:
				tableType = TableType.Family;
				break;
			default:
				break;
			}
			break;
		} while(true);
		
		do {
			numberOfPeople = inputHelper.getInteger("Input number of people in each table:");
			if(numberOfPeople > tableType.getCapacity()) {
				messageHelper.showMessage(tableType + " table can only fit " + tableType.getCapacity() + " people");
				continue;
			}
			break;
		} while(true);
		
		int result = 0;
		if(reservationStatus == ReservationStatus.InReserve) {
			result = reservationRepository.addReservation(restaurant.getRestaurantId(), employee.getEmployeeId(), customerName, reservationStatus, numberOfTable, tableType, numberOfPeople, new ArrayList<>());
		} else {
			ArrayList<ReservationDetail> reservationDetails = handleReservationDetail(customerName, reservationStatus, numberOfTable, tableType, numberOfPeople);
			result = reservationRepository.addReservation(restaurant.getRestaurantId(), employee.getEmployeeId(), customerName, reservationStatus, numberOfTable, tableType, numberOfPeople, reservationDetails);;
		}
		if(result == 0) {
			messageHelper.showMessage("Failed to add reservation");
			return;
		}
		messageHelper.showMessage("Successfully added new reservation for customer " + customerName);
	}
	
	private void updateReservation() {
		ArrayList<ReservationHeader> reservations = reservationRepository.getReservationByStatus(ReservationStatus.InReserve);
		displayReservations(reservations, "In Reserve");
		if(reservations.isEmpty()) return;
		ReservationHeader r = null;
		do {
			int input = inputHelper.getInteger("Input reservation id to order:");
			for(ReservationHeader reservation : reservations) {
				if(reservation.getReservationId() == input) {
					r = reservation;
					break;
				}
			}
			if(r == null) {
				messageHelper.showMessage("Please input a valid id!");
				continue;
			}
			break;
		} while(true);
		ArrayList<ReservationDetail> reservationDetails = handleReservationDetail(r.getCustomerName(), r.getReservationStatus(), r.getNumberOfTable(), r.getTableType(), r.getNumberOfPeople());
		
		int result = reservationRepository.updateToInOrder(r.getReservationId(), reservationDetails);
		if(result == 0) {
			messageHelper.showMessage("Failed to update reservation");
			return;
		}
		messageHelper.showMessage("Successfully updated reservation " + r.getReservationId());
	}
	
	private void checkOut() {
		ArrayList<ReservationHeader> reservations = reservationRepository.getReservationByStatus(ReservationStatus.InOrder);
		displayReservations(reservations, "In Order");
		if(reservations.isEmpty()) return;
		ReservationHeader r = null;
		do {
			int input = inputHelper.getInteger("Input reservation id to finalize:");
			for(ReservationHeader reservation : reservations) {
				if(reservation.getReservationId() == input) {
					r = reservation;
					break;
				}
			}
			if(r == null) {
				messageHelper.showMessage("Please input a valid id!");
				continue;
			}
			break;
		} while(true);
		int result = reservationRepository.updateReservationStatus(ReservationStatus.Finalized, r.getReservationId());
		if(result == 0) {
			messageHelper.showMessage("Failed to check out reservation");
			return;
		}
		ArrayList<ReservationDetail> reservationDetails = reservationRepository.getReservationDetail(r.getReservationId());
		if(reservationDetails == null) {
			messageHelper.showMessage("An unknown error has occured");
			return;
		}
		r.setReservationDetails(reservationDetails);
		System.out.println("Successfully finalized transaction!\n");
		displayReservationSummary(r);
	}
	
	private ArrayList<ReservationDetail> handleReservationDetail(String customerName, ReservationStatus reservationStatus, int numberOfTable, TableType tableType, int numberOfPeople) {
		ArrayList<ReservationDetail> reservationDetails = new ArrayList<>();
		ArrayList<Menu> menus = menuRepository.getAllMenu(restaurant.getRestaurantId());
		boolean flag = false;
		do {
			int choice = -1;
			System.out.println("Would you like to add an order?");
			System.out.println("1. Yes");
			System.out.println("2. No");
			choice = inputHelper.getInteger(">>");
			switch (choice) {
			case 1:
				Menu temp = null;
				int quantity = -1;
				
				do {
					displayMenu(menus);
					int idx = inputHelper.getInteger("Input menu id to order:");
					for(Menu menu: menus) {
						if(menu.getMenuId() == idx) {
							temp = menu;
							break;
						}
					}
				} while(temp == null);
				
				do {
					quantity = inputHelper.getInteger("Input order quantity:");
					if(quantity <= 0) {
						messageHelper.showMessage("Order quantity must be more than 0!");
						continue;
					}
					break;
				} while(true);
				
				boolean isUpdate = false;
				
				for(ReservationDetail rd : reservationDetails) {
					if(rd.getMenuID() == temp.getMenuId()) {
						rd.setQuantity(rd.getQuantity() + quantity);
						isUpdate = true;
						break;
					}
				}
				
				if(!isUpdate) reservationDetails.add(new ReservationDetail(temp.getMenuId(), quantity));
				
				continue;
			case 2:
				if(reservationDetails.isEmpty()) {
					messageHelper.showMessage("A reservation must have at least 1 order!");
					continue;
				}
				flag = true;
				break;
			default:
				messageHelper.showMessage("Invalid input!");
				continue;
			}
		} while(!flag);
		return reservationDetails;
	}
	
	private void displayReservations(ArrayList<ReservationHeader> reservations, String reservationType) {
		if(reservations.isEmpty()) {
			messageHelper.showMessage("There are currently no " + reservationType + " reservations");
			return;
		}
		System.out.println("Reservation list:");
		messageHelper.printMulti("=", 80, true);
		System.out.printf("| %-3s | %-8s | %-15s | %-6s | %-10s | %-6s | %-10s |\n", "Id", "Employee", "Customer", "Tables", "Table Type", "People", "Status");
		messageHelper.printMulti("=", 80, true);
		for(ReservationHeader r : reservations) {
			System.out.printf("| %-3d | %-8s | %-15s | %-6d | %-10s | %-6d | %-10s |\n", 
				r.getReservationId(), r.getEmployeeId(), messageHelper.displayLimit(r.getCustomerName(), 15) , 
				r.getNumberOfTable(), r.getTableType(), r.getNumberOfPeople(), 
				r.getReservationStatus());
		}
		messageHelper.printMulti("=", 80, true);
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
	
	private void displayReservationSummary(ReservationHeader reservationHeader) {
		long total = 0;
		System.out.println("Bill Summary");
		System.out.println("Handled by: " + reservationHeader.getEmployeeId());
		System.out.println("Customer: " + reservationHeader.getCustomerName());
		System.out.println("Orders:");
		int i = 1;
		messageHelper.printMulti("=", 72, true);
		System.out.printf("| %-3s | %-20s | %-12s | %-9s | %-12s |\n", "No.", "Menu Name", "Price", "Quantity", "Subtotal");
		messageHelper.printMulti("=", 72, true);
		for(ReservationDetail rd : reservationHeader.getReservationDetails()) {
			long subtotal = rd.getPrice() * rd.getQuantity();
			System.out.printf("| %-3d | %-20s | %-12s | %-9d | %-12d |\n", i++, rd.getMenuName(), rd.getPrice(), rd.getQuantity(), subtotal);
			total += subtotal;
		}
		messageHelper.printMulti("=", 72, true);
		messageHelper.showMessage("Total: Rp. " + total);
	}
	
}
