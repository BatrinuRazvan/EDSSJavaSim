package com.edss.models.helperclasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InitTablesHelper {

	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/edss";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "1234";

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

	public static void initUsersTable() {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			statement.executeUpdate("DROP TABLE IF EXISTS " + Constants.USERS_TABLE);

			String createTableQuery = "CREATE TABLE " + Constants.USERS_TABLE + " ( USERID VARCHAR(255),"
					+ "EMAIL VARCHAR(255)," + "LATITUDE DOUBLE," + "LONGITUDE DOUBLE," + "CLOSESTCITY VARCHAR(255),"
					+ "ENDPOINT VARCHAR(255)," + "P256 VARCHAR(255)," + "AUTH VARCHAR(255)" + ")";

			statement.executeUpdate(createTableQuery);

			System.out.println(Constants.USERS_TABLE + " table created successfully.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void initUserResponsesTable() {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			statement.executeUpdate("DROP TABLE IF EXISTS " + Constants.USERRESPONSES_TABLE);

			String createTableQuery = "CREATE TABLE " + Constants.USERRESPONSES_TABLE + " ("
					+ Constants.TIMESTAMP_CURRENT + "USERID VARCHAR(255)," + "DISASTER VARCHAR(255),"
					+ "STATE VARCHAR(255)" + ")";

			statement.executeUpdate(createTableQuery);

			System.out.println(Constants.USERRESPONSES_TABLE + " table created successfully.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
