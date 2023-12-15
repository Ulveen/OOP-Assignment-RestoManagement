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
		System.out.println("\nPress enter to continue");
		InputHelper.getInstance().getString();
	}

}
