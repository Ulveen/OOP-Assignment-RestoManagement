package restaurant;

import java.util.ArrayList;

import menu.Menu;
import table.Table;

public class Restaurant {

	private ArrayList<Table> tables;
	private ArrayList<Menu> menus;
	private RestaurantBranch branch;
	
	public Restaurant(ArrayList<Table> tables, ArrayList<Menu> menus, RestaurantBranch branch) {
		super();
		this.tables = tables;
		this.menus = menus;
		this.branch = branch;
	}

	public ArrayList<Table> getTables() {
		return tables;
	}
	
	public void setTables(ArrayList<Table> tables) {
		this.tables = tables;
	}
	
	public ArrayList<Menu> getMenus() {
		return menus;
	}
	
	public void setMenus(ArrayList<Menu> menus) {
		this.menus = menus;
	}
	
	public RestaurantBranch getBranch() {
		return branch;
	}
	
	public void setBranch(RestaurantBranch branch) {
		this.branch = branch;
	}
	
}
