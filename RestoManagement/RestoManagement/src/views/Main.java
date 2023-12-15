package views;

public class Main extends View {
	
	public Main() {
		show();
	}
	
	public static void main(String[] args) {
		new Main();
	}

	@Override
	public void show() {
		int choice = -1;
		do {
			System.out.println("LaperAh");
			System.out.println("1. Login");
			System.out.println("2. Register");
			System.out.println("3. Exit");
			System.out.print(">> ");
			choice = inputHelper.getInteger();
			System.out.println("");
			switch (choice) {
			case 1:
				new Login();
				break;
			case 2:
				new Register();
				break;
			case 3:
				System.out.println("Thank you for using LaperAh!");
				break;
			default:
				messageHelper.showMessage("Invalid input!");
			}
		} while(choice != 2);
	}

}
