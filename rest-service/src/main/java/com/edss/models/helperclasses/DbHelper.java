package com.edss.models.helperclasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.edss.models.CityMarker;
import com.edss.models.MessageNotification;

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

	public static void initNotificationsDatabase() {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			statement.executeUpdate("DROP TABLE IF EXISTS " + Constants.NOTIFICATIONS_TABLE);

			String createTableQuery = "CREATE TABLE " + Constants.NOTIFICATIONS_TABLE + " ("
					+ Constants.ID_AUTO_INCREMENT + Constants.TIMESTAMP_CURRENT + "CITY VARCHAR(255),"
					+ "TITLE VARCHAR(255)," + "COLOR VARCHAR(255)," + "SEVERITY VARCHAR(255)," + "RANGEKM INT,"
					+ "DESCRIPTION VARCHAR(255) " + ")";

			statement.executeUpdate(createTableQuery);

			System.out.println(Constants.NOTIFICATIONS_TABLE + " table created successfully.");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void addMessageNotification(String city, String title, String color, String severity, int rangeKm,
			String description) {
// Assuming Constants.NOTIFICATIONS_TABLE is your table name and it has columns 
// city, title, color, severity, range_km, and description accordingly.
		String sqlStatement = "INSERT INTO " + Constants.NOTIFICATIONS_TABLE
				+ " (city, title, color, severity, rangekm, description) VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

// Set the values for each placeholder
			preparedStatement.setString(1, city);
			preparedStatement.setString(2, title);
			preparedStatement.setString(3, color);
			preparedStatement.setString(4, severity);
			preparedStatement.setInt(5, rangeKm); // rangeKm is treated as an integer
			preparedStatement.setString(6, description);

// Execute the update
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void initMarkersDatabase() {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			statement.executeUpdate("DROP TABLE IF EXISTS " + Constants.MARKERS_TABLE);

			String createTableQuery = "CREATE TABLE " + Constants.MARKERS_TABLE + " (" + Constants.ID_AUTO_INCREMENT
					+ "CITY VARCHAR(255)," + "LATITUDE DOUBLE," + "LONGITUDE DOUBLE" + ")";

			statement.executeUpdate(createTableQuery);

			System.out.println(Constants.MARKERS_TABLE + " table created successfully.");

			Map<String, List<Double>> cities = Constants.cities;
			Set<String> keySet = cities.keySet();
			keySet.forEach(city -> {
				String populateTable = "INSERT INTO " + Constants.MARKERS_TABLE + "(CITY, LATITUDE, LONGITUDE)"
						+ " VALUES ('" + city + "', " + cities.get(city).get(0) + ", " + cities.get(city).get(1) + ")";
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

	public static List<CityMarker> getCities() {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			String createTableQuery = "SELECT * FROM " + Constants.MARKERS_TABLE;

			ResultSet result = statement.executeQuery(createTableQuery);
			List<CityMarker> markers = new ArrayList<>();
			while (result.next()) {
				CityMarker marker = new CityMarker(result.getString(2), result.getDouble(3), result.getDouble(4));
				markers.add(marker);
			}
			return markers;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static List<MessageNotification> getMessageNotifications() {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			String createTableQuery = "SELECT * FROM " + Constants.NOTIFICATIONS_TABLE;

			ResultSet result = statement.executeQuery(createTableQuery);
			List<MessageNotification> messages = new ArrayList<>();
			while (result.next()) {
				MessageNotification message = new MessageNotification(result.getString(4), result.getString(3),
						result.getString(5), result.getString(6), result.getInt(7), result.getString(8));
				messages.add(message);
			}
			return messages;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}
}
