package reservation;

public class ReservationDetail {

	private int menuID;
	private int quantity;
	private String menuName;
	private long price;

	public ReservationDetail(int menuID, int quantity) {
		super();
		this.menuID = menuID;
		this.quantity = quantity;
	}

	public int getMenuID() {
		return menuID;
	}

	public void setMenuID(int menuID) {
		this.menuID = menuID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

}
