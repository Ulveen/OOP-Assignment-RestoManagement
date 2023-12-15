package Utils;

import java.util.Scanner;

public class InputHelper {

	private static InputHelper inputHelper = null;
	private static final Scanner scan = new Scanner(System.in);
	
	private InputHelper() {
		
	}
	
	public static synchronized InputHelper getInstance() {
		if(inputHelper == null) {
			inputHelper = new InputHelper();
		}
		return inputHelper;
	}
	
	public int getInteger() {
		int input = -1;
		try {
			input = scan.nextInt();
		} catch (Exception e) {
			
		} finally {
			scan.nextLine();
		}
		return input;
	}
	
	public String getString() {
		return scan.nextLine();
	}
	
	public double getDouble() {
		double input = -1;
		try {
			input = scan.nextDouble();
		} catch (Exception e) {
			
		} finally {
			scan.nextLine();
		}
		return input;
	}

}
