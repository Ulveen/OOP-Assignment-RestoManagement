package table;

public class Table {

	private TableType type;
	private int capacity;
	
	public Table(TableType type, int capacity) {
		super();
		this.type = type;
		this.capacity = capacity;
	}

	public TableType getType() {
		return type;
	}

	public void setType(TableType type) {
		this.type = type;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}
