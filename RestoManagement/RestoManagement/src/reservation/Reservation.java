package reservation;

import java.util.ArrayList;

import table.Table;

public class Reservation {

	private String name;
	private int tableCount;
	private ArrayList<Table> tables;
	private ArrayList<Integer> numberOfPeople;
	
	public Reservation(String name, int tableCount, ArrayList<Table> tables, ArrayList<Integer> numberOfPeople) {
		super();
		this.name = name;
		this.tableCount = tableCount;
		this.tables = tables;
		this.numberOfPeople = numberOfPeople;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTableCount() {
		return tableCount;
	}

	public void setTableCount(int tableCount) {
		this.tableCount = tableCount;
	}

	public ArrayList<Table> getTables() {
		return tables;
	}

	public void setTables(ArrayList<Table> tables) {
		this.tables = tables;
	}

	public ArrayList<Integer> getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(ArrayList<Integer> numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}
	
}
