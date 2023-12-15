package menu;

public class LocalMenu extends Menu {

	private String origin;
	private String uniqueTraits;
	
	public LocalMenu(int id, String name, Integer price, String origin, String uniqueTraits) {
		super(id, name, price);
		this.origin = origin;
		this.uniqueTraits = uniqueTraits;
	}

	public String getOrigin() {
		return origin;
	}
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public String getUniqueness() {
		return uniqueTraits;
	}
	
	public void setUniqueness(String uniqueness) {
		this.uniqueTraits = uniqueness;
	}
	
}
