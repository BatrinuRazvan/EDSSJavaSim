package com.edss.simulation.helperclasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbHelper {

	private static String JDBC_URL = "jdbc:mysql://localhost:3306/edss";
	private static String USERNAME = "root";
	private static String PASSWORD = "1234";

	public void initUserResponsesDatabase() {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			statement.executeUpdate("DROP TABLE IF EXISTS SIMULATION");

			String createTableQuery = "CREATE TABLE SIMULATION (" + "id INT AUTO_INCREMENT PRIMARY KEY,"
					+ "TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP," + "username VARCHAR(255),"
					+ "QUESTION VARCHAR(255)," + "RESPONSE VARCHAR(255)," + ")";

			statement.executeUpdate(createTableQuery);

			System.out.println("SIMULATION table created successfully.");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
