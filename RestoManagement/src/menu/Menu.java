package menu;

public class Menu {

	private int menuId;
	private String menuName;
	private long price;
	
	public Menu(int menuId, String menuName, long price) {
		super();
		this.menuId = menuId;
		this.menuName = menuName;
		this.price = price;
	}

	public int getMenuId() {
		return menuId;
	}
	
	public void setMenuId(int menuId) {
		this.menuId = menuId;
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
