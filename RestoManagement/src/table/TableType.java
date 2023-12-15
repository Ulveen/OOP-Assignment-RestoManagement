package table;

public enum TableType {

	Romantic(2),
	General(4),
	Family(10);
	
    private final int capacity;

    private TableType(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
	
}
