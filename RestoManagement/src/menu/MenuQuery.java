package menu;

public class MenuQuery {

	protected final static String GET_ALL_MENUS = "SELECT mm.MenuID, mm.MenuName, mm.Price, lmd.UniqueTraits, lmd.Origin, smd.MenuStory FROM msmenu mm LEFT JOIN localmenudetail lmd ON mm.MenuID = lmd.MenuID LEFT JOIN specialmenudetail smd ON mm.MenuID = smd.MenuID WHERE mm.RestaurantID = ?";
	protected final static String ADD_MENU = "INSERT INTO MsMenu(RestaurantID, MenuCategory, MenuName, Price) VALUES (?, ?, ?, ?)";
	protected final static String ADD_LOCAL_MENU_DETAIL = "INSERT INTO LocalMenuDetail VALUES (?, ?, ?)";
	protected final static String ADD_SPECIAL_MENU_DETAIL = "INSERT INTO SpecialMenuDetail VALUES(?, ?)";
	protected final static String GET_LAST_MENU_ID = "SELECT MenuID FROM MsMenu ORDER BY MenuID DESC LIMIT 1";
	protected final static String DELETE_MENU = "DELETE FROM MsMenu WHERE MenuID = ?";
	protected final static String UPDATE_MENU = "UPDATE MsMenu SET MenuName = ?, Price = ? WHERE MenuID = ?";
	protected final static String UPDATE_LOCAL_MENU_DETAIL = "UPDATE LocalMenuDetail SET UniqueTraits = ?, Origin = ? WHERE MenuID = ?";
	protected final static String UPDATE_SPECIAL_MENU_DETAIL = "UPDATE SpecialMenuDetail SET MenuStory = ? WHERE MenuID = ?";

}