package Utils;

public class MessageHelper {

	private static MessageHelper messageHelper = null;
	
	private MessageHelper() {
		
	}
	
	public static synchronized MessageHelper getInstance() {
		if(messageHelper == null) {
			messageHelper = new MessageHelper();
		}
		return messageHelper;
	}
	
	public void showMessage(String message) {
		System.out.println(message);
		InputHelper.getInstance().getString("Press enter to continue...");
	}
	
	public void printMulti(String word, int count, boolean newLine) {
		for(int i = 0; i < count; i++) {
			System.out.print(word);
		}
		if(newLine) {
			System.out.println();
		}
	}
	
	public String displayLimit(String word, int limit) {
		if(word.length() <= limit) {
			return word;
		}
		return word.substring(0, limit - 3) + "...";
	}

}
