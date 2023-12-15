package menu;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import database.Database;

public class MenuRepository {

	private static MenuRepository menuRepository = null;
	private final Database database = Database.getConnection();
	
	private MenuRepository() {
		
	}
	
	public static MenuRepository getInstance() {
		if(menuRepository == null) menuRepository = new MenuRepository();
		return menuRepository;
	}

	private Menu castToMenu(ResultSet resultSet) {
		Menu menu = null;
		try {
			int menuId = resultSet.getInt(1);
			String menuName = resultSet.getString(2);
			long price = resultSet.getLong(3);
			String menuStory = resultSet.getString(6);
			
			if(menuStory != null) {
				return new SpecialMenu(menuId, menuName, price, menuStory);
			}
			
			String uniqueTraits = resultSet.getString(4);
			String origins = resultSet.getString(5);
			if(uniqueTraits != null && origins != null) {
				return new LocalMenu(menuId, menuName, price, origins, uniqueTraits);
			}
			
			return new Menu(menuId, menuName, price);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menu;
	}
	
	public ArrayList<Menu> getAllMenu(int restaurantID) {
		ArrayList<Menu> menus = new ArrayList<>();
		try {
			PreparedStatement selectMenu = database.prepareStatement(MenuQuery.GET_ALL_MENUS);
			selectMenu.setInt(1, restaurantID);
			ResultSet resultSet = selectMenu.executeQuery();
			while(resultSet.next()) {
				Menu menu = castToMenu(resultSet);
				menus.add(menu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menus;
	}
	
	public int addMenu(int restaurantId, MenuCategory menuCategory, String name, long price) {
		try {
			PreparedStatement insertIntoMenu = database.prepareStatement(MenuQuery.ADD_MENU);
			insertIntoMenu.setInt(1, restaurantId);
			insertIntoMenu.setString(2, menuCategory.toString());
			insertIntoMenu.setString(3, name);
			insertIntoMenu.setLong(4, price);
			return insertIntoMenu.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int addLocalMenu(int restaurantId, MenuCategory menuCategory, String name, long price, String uniqueTraits, String origins) {
		int result = addMenu(restaurantId, menuCategory, name, price);
		if(result == 0) return result;
		try {
			ResultSet selectLastMenu = database.executeQuery(MenuQuery.GET_LAST_MENU_ID);
			PreparedStatement insertIntoLocalMenuDetail = database.prepareStatement(MenuQuery.ADD_LOCAL_MENU_DETAIL);
			if(!selectLastMenu.next()) return 0;
			int menuId = selectLastMenu.getInt(1);
			insertIntoLocalMenuDetail.setInt(1, menuId);
			insertIntoLocalMenuDetail.setString(2, uniqueTraits);
			insertIntoLocalMenuDetail.setString(3, origins);
			return insertIntoLocalMenuDetail.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int addSpecialMenu(int restaurantId, MenuCategory menuCategory, String name, long price, String story) {
		int result = addMenu(restaurantId, menuCategory, name, price);
		if(result == 0) return result;
		try {
			ResultSet selectLastMenu = database.executeQuery(MenuQuery.GET_LAST_MENU_ID);
			PreparedStatement insertIntoSpecialMenuDetail = database.prepareStatement(MenuQuery.ADD_SPECIAL_MENU_DETAIL);
			if(!selectLastMenu.next()) return 0;
			int menuId = selectLastMenu.getInt(1);
			insertIntoSpecialMenuDetail.setInt(1, menuId);
			insertIntoSpecialMenuDetail.setString(2, story);
			return insertIntoSpecialMenuDetail.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int deleteMenu(int id) {
		try {
			PreparedStatement deleteMenu = database.prepareStatement(MenuQuery.DELETE_MENU);
			deleteMenu.setInt(1, id);
			return deleteMenu.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updateMenu(Menu menu) {
		try {
			PreparedStatement updateMenu = database.prepareStatement(MenuQuery.UPDATE_MENU);
			updateMenu.setString(1, menu.getMenuName());
			updateMenu.setLong(2, menu.getPrice());
			updateMenu.setInt(3, menu.getMenuId());
			return updateMenu.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updateLocalMenu(LocalMenu menu) {
		try {
			int result = updateMenu(menu);
			if(result == 0) return result;
			PreparedStatement updateLocalMenuDetail = database.prepareStatement(MenuQuery.UPDATE_LOCAL_MENU_DETAIL);
			updateLocalMenuDetail.setString(1, menu.getUniqueness());
			updateLocalMenuDetail.setString(2, menu.getOrigin());
			updateLocalMenuDetail.setInt(3, menu.getMenuId());
			return updateLocalMenuDetail.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updateSpecialMenu(SpecialMenu menu) {
		try {
			int result = updateMenu(menu);
			if(result == 0) return result;
			PreparedStatement updateSpecialMenuDetail = database.prepareStatement(MenuQuery.UPDATE_SPECIAL_MENU_DETAIL);
			updateSpecialMenuDetail.setString(1, menu.getStory());
			updateSpecialMenuDetail.setInt(2, menu.getMenuId());
			return updateSpecialMenuDetail.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
