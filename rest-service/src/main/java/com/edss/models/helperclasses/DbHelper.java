package com.edss.models.helperclasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DbHelper {

	private static String JDBC_URL = "jdbc:mysql://localhost:3306/edss";
	private static String USERNAME = "root";
	private static String PASSWORD = "1234";

	public void initUserResponsesDatabase() {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			statement.executeUpdate("DROP TABLE IF EXISTS " + Constants.USERRESPONSES_TABLE);

			String createTableQuery = "CREATE TABLE " + Constants.USERRESPONSES_TABLE + " ("
					+ Constants.ID_AUTO_INCREMENT + Constants.TIMESTAMP_CURRENT + "USERNAME VARCHAR(255),"
					+ "QUESTION VARCHAR(255)," + "RESPONSE VARCHAR(255)," + ")";

			statement.executeUpdate(createTableQuery);

			System.out.println(Constants.USERRESPONSES_TABLE + " table created successfully.");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void addUserRespons(String email, String question, String response) {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			String sqlStatement = "INSERT INTO " + Constants.USERRESPONSES_TABLE + " (" + email + ", " + question + ", "
					+ response + ") VALUES (?, ?, ?)";

			statement.executeUpdate(sqlStatement);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void initNotificationsDatabase() {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			statement.executeUpdate("DROP TABLE IF EXISTS " + Constants.NOTIFICATIONS_TABLE);

			String createTableQuery = "CREATE TABLE " + Constants.NOTIFICATIONS_TABLE + " ("
					+ Constants.ID_AUTO_INCREMENT + Constants.TIMESTAMP_CURRENT + "CITY VARCHAR(255),"
					+ "TITLE VARCHAR(255)" + "SEVERITY VARCHAR(255)," + "RANGEKM INT," + "DESCRIPTION VARCHAR(255),"
					+ ")";

			statement.executeUpdate(createTableQuery);

			System.out.println(Constants.NOTIFICATIONS_TABLE + " table created successfully.");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void addMessageNotification(String city, String title, String severity, int range,
			String description) {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			String sqlStatement = "INSERT INTO " + Constants.NOTIFICATIONS_TABLE + " (" + city + ", " + title + ", "
					+ severity + ", " + range + ", " + description + ", " + ") VALUES (?, ?, ?, ?, ?)";

			statement.executeUpdate(sqlStatement);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void initMarkersDatabase() {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			statement.executeUpdate("DROP TABLE IF EXISTS " + Constants.MARKERS_TABLE);

			String createTableQuery = "CREATE TABLE " + Constants.MARKERS_TABLE + " (" + Constants.ID_AUTO_INCREMENT
					+ Constants.TIMESTAMP_CURRENT + "CITY VARCHAR(255)," + "LATITUDE DOUBLE," + "LOGITUDE DOUBLE,"
					+ ")";

			statement.executeUpdate(createTableQuery);

			System.out.println(Constants.MARKERS_TABLE + " table created successfully.");

			Map<String, List<Double>> cities = Constants.cities;
			Set<String> keySet = cities.keySet();
			keySet.forEach(city -> {
				String populateTable = "INSERT INTO " + Constants.MARKERS_TABLE + " (" + city + ", "
						+ cities.get(city).get(0) + ", " + cities.get(city).get(1) + ") VALUES (?, ?, ?)";
				try {
					statement.executeUpdate(populateTable);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
