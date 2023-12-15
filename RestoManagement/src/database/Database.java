package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String DATABASE = "RestoManagement";
	private final String HOST = "localhost:3306";
	private final String CONECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	private static Database database = null;
	private Connection connection = null;
	private Statement statement = null;
	
	private Database() {
		try {
			connection = DriverManager.getConnection(CONECTION, USERNAME, PASSWORD);
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized Database getConnection() {
		if(database == null) {
			database = new Database();
		}
		return database;
	}
	
	public PreparedStatement prepareStatement(String query) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}
	
	
	public ResultSet executeQuery(String query) {
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

}
