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
	
	public int getInteger(String prompt) {
		System.out.print(prompt + " ");
		int input = -1;
		try {
			input = scan.nextInt();
		} catch (Exception e) {
			
		} finally {
			scan.nextLine();
			System.out.println();
		}
		return input;
	}
	
	public String getString(String prompt) {
		System.out.print(prompt + " ");
		String input = scan.nextLine();
		System.out.println();
		return input;
	}
	
	public double getDouble(String prompt) {
		System.out.print(prompt + " ");
		double input = -1;
		try {
			input = scan.nextDouble();
		} catch (Exception e) {
			
		} finally {
			scan.nextLine();
			System.out.println();
		}
		return input;
	}
	
	public long getLong(String prompt) {
		System.out.print(prompt + " ");
		long input = -1;
		try {
			input = scan.nextLong();
		} catch (Exception e) {
			
		} finally {
			scan.nextLine();
			System.out.println();
		}
		return input;
	}

}
