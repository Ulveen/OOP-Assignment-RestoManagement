package views;

import Utils.InputHelper;
import Utils.MessageHelper;

public abstract class View {

	protected final InputHelper inputHelper = InputHelper.getInstance();
	protected final MessageHelper messageHelper = MessageHelper.getInstance();
	
	public View() {
		
	}
	
	public abstract void show();

}
