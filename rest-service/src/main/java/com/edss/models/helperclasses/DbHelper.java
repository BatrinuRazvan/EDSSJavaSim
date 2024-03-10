package com.edss.models.helperclasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.edss.models.CityMarker;

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

	public static List<CityMarker> getCities() {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			String createTableQuery = "SELECT * FROM " + Constants.MARKERS_TABLE;

			ResultSet result = statement.executeQuery(createTableQuery);
			List<CityMarker> markers = new ArrayList<>();
			while (result.next()) {
				CityMarker marker = new CityMarker(result.getString(1), result.getDouble(2), result.getDouble(3));
				markers.add(marker);
			}
			return markers;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}
}
