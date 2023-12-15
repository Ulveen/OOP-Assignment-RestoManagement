package menu;

public class SpecialMenu extends Menu {

	private String story;
	
	public SpecialMenu(int id, String name, long price, String story) {
		super(id, name, price);
		this.story = story;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

}
