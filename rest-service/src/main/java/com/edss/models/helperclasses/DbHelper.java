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
import com.edss.models.UserLocation;

public class DbHelper {

	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/edss";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "1234";
	private static Double baseDegreeChange = 0.09;

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
					+ "DESCRIPTION VARCHAR(255)," + "DEGREECHANGE DOUBLE" + ")";

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
				+ " (city, title, color, severity, rangekm, description, degreechange) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

			preparedStatement.setString(1, city);
			preparedStatement.setString(2, title);
			preparedStatement.setString(3, color);
			preparedStatement.setString(4, severity);
			preparedStatement.setInt(5, rangeKm);
			preparedStatement.setString(6, description);
			preparedStatement.setDouble(7, rangeKm * baseDegreeChange);

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
						result.getString(5), result.getString(6), result.getInt(7), result.getString(8),
						result.getDouble(9));
				messages.add(message);
			}
			return messages;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static List<MessageNotification> getLocalMessageNotifications(UserLocation userLocation) {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			double userLat = userLocation.getLatitude();
			double userLon = userLocation.getLongitude();
			List<MessageNotification> filteredMessages = new ArrayList<>();

			String selectCity = "SELECT * FROM" + Constants.MARKERS_TABLE + "WHERE CAST( LATITUDE AS CHAR ) LIKE '"
					+ (int) userLat + "%' AND CAST( LONGITUDE AS CHAR ) LIKE '" + (int) userLon + "%' ";
			ResultSet resultCities = statement.executeQuery(selectCity);

			while (resultCities.next()) {
				CityMarker city = new CityMarker(resultCities.getString(2), resultCities.getDouble(3),
						resultCities.getDouble(4));
				String selectMessageNotification = "SELECT * FROM" + Constants.NOTIFICATIONS_TABLE + "WHERE CITY = '"
						+ city.getCityName() + "' ";
				ResultSet messagesForCity = statement.executeQuery(selectMessageNotification);

				while (messagesForCity.next()) {

					MessageNotification message = new MessageNotification(messagesForCity.getString(4),
							messagesForCity.getString(3), messagesForCity.getString(5), messagesForCity.getString(6),
							messagesForCity.getInt(7), messagesForCity.getString(8), messagesForCity.getDouble(9));

					double upperLatThreshold = city.getLatitude() + message.getDegreeChange();
					double lowerLatThreshold = city.getLatitude() - message.getDegreeChange();
					if (userLat < upperLatThreshold && userLat > lowerLatThreshold) {
						double upperLonThreshold = city.getLongitude() + message.getDegreeChange();
						double lowerLonThreshold = city.getLongitude() - message.getDegreeChange();
						if (userLat < upperLonThreshold && userLat > lowerLonThreshold) {
							filteredMessages.add(message);
						}
					}
				}
			}
			return filteredMessages;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}
}
