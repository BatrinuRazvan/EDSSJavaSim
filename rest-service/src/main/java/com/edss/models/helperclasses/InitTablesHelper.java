package com.edss.models.helperclasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
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
					+ "USERTYPE VARCHAR(255)," + "ENDPOINT VARCHAR(255)," + "P256 VARCHAR(255)," + "AUTH VARCHAR(255)"
					+ ")";

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
					+ Constants.TIMESTAMP_CURRENT + "USERID VARCHAR(255)," + "CITY VARCHAR(255),"
					+ "DISASTER VARCHAR(255)," + "STATE VARCHAR(255)" + ")";

			statement.executeUpdate(createTableQuery);

			System.out.println(Constants.USERRESPONSES_TABLE + " table created successfully.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void initDecisionResponsesTable() {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			statement.executeUpdate("DROP TABLE IF EXISTS " + Constants.DECISIONRESPONSES_TABLE);

			String createTableQuery = "CREATE TABLE " + Constants.DECISIONRESPONSES_TABLE + " ("
					+ Constants.TIMESTAMP_CURRENT + "CITY VARCHAR(255)," + "DISASTER VARCHAR(255),"
					+ "STATE VARCHAR(255)" + ")";

			statement.executeUpdate(createTableQuery);

			System.out.println(Constants.DECISIONRESPONSES_TABLE + " table created successfully.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void initExitPointsTable() {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			statement.executeUpdate("DROP TABLE IF EXISTS " + Constants.EXITPOINTS_TABLE);

			String createTableQuery = "CREATE TABLE " + Constants.EXITPOINTS_TABLE + " (" + Constants.ID_AUTO_INCREMENT
					+ "CITY VARCHAR(255)," + "LATITUDE DOUBLE," + "LONGITUDE DOUBLE" + ")";

			statement.executeUpdate(createTableQuery);

			System.out.println(Constants.EXITPOINTS_TABLE + " table created successfully.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		populateExitPointsTable();
	}

	private static void populateExitPointsTable() {
		String sqlStatement = "INSERT INTO " + Constants.EXITPOINTS_TABLE
				+ " (city, latitude, longitude) VALUES (?, ?, ?)";

		Map<String, String> maplatlon = new HashMap<>();
		maplatlon.put("45.707165", "21.187585");
		maplatlon.put("45.763299", "21.178882");
		maplatlon.put("45.778510", "21.210028");
		maplatlon.put("45.787344", "21.217003");
		maplatlon.put("45.785867", "21.236797");
		maplatlon.put("45.769992", "21.270408");
		maplatlon.put("45.729941", "21.266177");
		maplatlon.put("45.706999", "21.233715");

		maplatlon.keySet().forEach(lat -> {

			try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
					Constants.PASSWORD);
					PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

				preparedStatement.setString(1, "Timisoara");
				preparedStatement.setDouble(2, Double.valueOf(lat));
				preparedStatement.setDouble(3, Double.valueOf(maplatlon.get(lat)));

				preparedStatement.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		});
	}

	public static void initDiagnosticsTable() {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			statement.executeUpdate("DROP TABLE IF EXISTS " + Constants.DIAGNOSTICS_TABLE);

			String createTableQuery = "CREATE TABLE " + Constants.DIAGNOSTICS_TABLE + " (" + Constants.ID_AUTO_INCREMENT
					+ "NAME VARCHAR(255)," + "TOTAL INT" + ")";

			statement.executeUpdate(createTableQuery);

			System.out.println(Constants.DIAGNOSTICS_TABLE + " table created successfully.");

//			initOtherOption(Constants.DIAGNOSTICS_TABLE, connection, statement);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void initSymptomsTable() {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			statement.executeUpdate("DROP TABLE IF EXISTS " + Constants.SYMPTOMS_TABLE);

			String createTableQuery = "CREATE TABLE " + Constants.SYMPTOMS_TABLE + " (" + "DIAGNOSTICNAME VARCHAR(255),"
					+ "SYMPTOM VARCHAR(255)" + ")";

			statement.executeUpdate(createTableQuery);

			System.out.println(Constants.SYMPTOMS_TABLE + " table created successfully.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	private static void initOtherOption(String table, Connection connection, Statement statement) throws SQLException {
//		String sqlStatement = "INSERT INTO " + table + " (name, total) VALUES (?, ?)";
//
//		PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
//
//		preparedStatement.setString(1, "OTHER");
//		preparedStatement.setDouble(2, 0);
//
//		preparedStatement.executeUpdate();
//
//	}
}
