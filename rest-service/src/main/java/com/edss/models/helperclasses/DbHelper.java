package com.edss.models.helperclasses;

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

			String createTableQuery = "CREATE TABLE USERRESPONSES (" + "id INT AUTO_INCREMENT PRIMARY KEY,"
					+ "TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP," + "USERNAME VARCHAR(255),"
					+ "QUESTION VARCHAR(255)," + "RESPONSE VARCHAR(255)," + ")";

			statement.executeUpdate(createTableQuery);

			System.out.println("USERRESPONSES table created successfully.");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void addUserRespons(String email, String question, String response) {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			String sqlStatement = "INSERT INTO USERRESPONSES (" + email + ", " + question + ", " + response
					+ ") VALUES (?, ?, ?)";

			statement.executeUpdate(sqlStatement);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void initNotificationsDatabase() {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			statement.executeUpdate("DROP TABLE IF EXISTS SIMULATION");

			String createTableQuery = "CREATE TABLE NOTIFICATIONS (" + "id INT AUTO_INCREMENT PRIMARY KEY,"
					+ "TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP," + "CITY VARCHAR(255)," + "SEVERITY VARCHAR(255),"
					+ "RANGEKM VARCHAR(255)," + "DESCRIPTION VARCHAR(255)," + ")";

			statement.executeUpdate(createTableQuery);

			System.out.println("NOTIFICATIONS table created successfully.");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
