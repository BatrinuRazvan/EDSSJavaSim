package com.edss.models.helperclasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.edss.models.CityMarker;
import com.edss.models.DecisionResponse;
import com.edss.models.EdssSubscription;
import com.edss.models.MessageNotification;
import com.edss.models.SimulationDayData;
import com.edss.models.User;
import com.edss.models.UserLocation;
import com.edss.models.UserResponse;

public class DbHelper {

	private static Double baseDegreeChange = 0.09;

	public static void addUserRespons(String email, String question, String response) {
		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); Statement statement = connection.createStatement()) {

			String sqlStatement = "INSERT INTO " + Constants.USERRESPONSES_TABLE + " (" + email + ", " + question + ", "
					+ response + ") VALUES (?, ?, ?)";

			statement.executeUpdate(sqlStatement);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addMessageNotification(String city, String title, String color, String severity, int rangeKm,
			String description) {
		String sqlStatement = "INSERT INTO " + Constants.NOTIFICATIONS_TABLE
				+ " (city, title, color, severity, rangekm, description, degreechange) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

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

	public static List<CityMarker> getCities() {
		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); Statement statement = connection.createStatement()) {

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
		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); Statement statement = connection.createStatement()) {

			String createTableQuery = "SELECT * FROM " + Constants.NOTIFICATIONS_TABLE;

			ResultSet result = statement.executeQuery(createTableQuery);
			List<MessageNotification> messages = new ArrayList<>();
			while (result.next()) {
				MessageNotification message = new MessageNotification(result.getInt(1), result.getString(4),
						result.getString(3), result.getString(5), result.getString(6), result.getInt(7),
						result.getString(8), result.getDouble(9));
				messages.add(message);
			}
			return messages;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static List<MessageNotification> getLocalMessageNotifications(UserLocation userLocation) {
		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD)) {
			double userLat = userLocation.getLatitude();
			double userLon = userLocation.getLongitude();
			List<MessageNotification> filteredMessages = new ArrayList<>();

			String selectCity = "SELECT * FROM " + Constants.MARKERS_TABLE + " WHERE CAST(LATITUDE AS CHAR) LIKE '"
					+ (int) userLat + "%' AND CAST(LONGITUDE AS CHAR) LIKE '" + (int) userLon + "%'";

			try (Statement statementForCities = connection.createStatement();
					ResultSet resultCities = statementForCities.executeQuery(selectCity)) {

				while (resultCities.next()) {
					CityMarker city = new CityMarker(resultCities.getString(2), resultCities.getDouble(3),
							resultCities.getDouble(4));

					String selectMessageNotification = "SELECT * FROM " + Constants.NOTIFICATIONS_TABLE
							+ " WHERE CITY = '" + city.getCityName() + "'";

					try (Statement statementForMessages = connection.createStatement();
							ResultSet messagesForCity = statementForMessages.executeQuery(selectMessageNotification)) {

						while (messagesForCity.next()) {
							MessageNotification message = new MessageNotification(messagesForCity.getInt(1),
									messagesForCity.getString(4), messagesForCity.getString(3),
									messagesForCity.getString(5), messagesForCity.getString(6),
									messagesForCity.getInt(7), messagesForCity.getString(8),
									messagesForCity.getDouble(9));

							double upperLatThreshold = city.getLatitude() + message.getDegreeChange();
							double lowerLatThreshold = city.getLatitude() - message.getDegreeChange();
							if (userLat < upperLatThreshold && userLat > lowerLatThreshold) {
								double upperLonThreshold = city.getLongitude() + message.getDegreeChange();
								double lowerLonThreshold = city.getLongitude() - message.getDegreeChange();
								if (userLon < upperLonThreshold && userLon > lowerLonThreshold) {
									filteredMessages.add(message);
								}
							}
						}
					}
				}
			}
			return filteredMessages;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void saveUser(User newUser) {

		String sqlStatement = "INSERT INTO " + Constants.USERS_TABLE
				+ " (userid, email, latitude, longitude, closestcity, usertype) VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

			preparedStatement.setString(1, newUser.getUserId());
			preparedStatement.setString(2, newUser.getEmail());
			preparedStatement.setDouble(3, newUser.getCurrentLocation().getLatitude());
			preparedStatement.setDouble(4, newUser.getCurrentLocation().getLongitude());
			preparedStatement.setString(5, newUser.getClosestCity());
			preparedStatement.setString(6, newUser.getUserType());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<User> getAllUsers() {
		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); Statement statement = connection.createStatement()) {

			String createTableQuery = "SELECT * FROM " + Constants.USERS_TABLE;

			ResultSet result = statement.executeQuery(createTableQuery);
			List<User> users = new ArrayList<>();
			while (result.next()) {
				User user = new User(result.getString(1), result.getString(2),
						new UserLocation(result.getDouble(3), result.getDouble(4)), result.getString(6));
				users.add(user);
			}
			return users;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void saveSubscription(EdssSubscription subscription) {
		String sqlStatement = "UPDATE " + Constants.USERS_TABLE
				+ " SET endpoint = ?, p256 = ?, auth = ? WHERE userid = ?";

		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

			preparedStatement.setString(1, subscription.getEndpoint());
			preparedStatement.setString(2, subscription.getP256dh());
			preparedStatement.setString(3, subscription.getAuth());
			preparedStatement.setString(4, subscription.getUserId());

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows > 0) {
				System.out.println("Subscription updated successfully for user: " + subscription.getUserId());
			} else {
				System.out.println("No subscription was updated. User ID might not exist in the table: "
						+ subscription.getUserId());
			}

		} catch (SQLException e) {
			System.out.println("Error updating subscription for user: " + subscription.getUserId());
			e.printStackTrace();
		}
	}

	public static List<EdssSubscription> getAllSubscriptions() {
		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); Statement statement = connection.createStatement()) {

			String createTableQuery = "SELECT * FROM " + Constants.SUBSCRIPTION_TABLE;

			ResultSet result = statement.executeQuery(createTableQuery);
			List<EdssSubscription> subs = new ArrayList<>();
			while (result.next()) {
				EdssSubscription sub = new EdssSubscription(result.getString(1), result.getString(2),
						result.getString(3), result.getString(4));
				subs.add(sub);
			}
			return subs;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<User> getUsersInArea(MessageNotification notification) {
		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD)) {
			List<User> users = new ArrayList<>();

			CityMarker cityMarker = null;
			String selectCityMarker = "SELECT * FROM " + Constants.MARKERS_TABLE + " WHERE CITY = '"
					+ notification.getCity() + "' ";

			try (Statement statementForCities = connection.createStatement();
					ResultSet resultCityMarker = statementForCities.executeQuery(selectCityMarker)) {

				while (resultCityMarker.next()) {
					cityMarker = new CityMarker(resultCityMarker.getString(2), resultCityMarker.getDouble(3),
							resultCityMarker.getDouble(4));

					String selectUsers = "SELECT * FROM " + Constants.USERS_TABLE;

					try (Statement statementForMessages = connection.createStatement();
							ResultSet resultUsers = statementForMessages.executeQuery(selectUsers)) {

						while (resultUsers.next()) {
							UserLocation location = new UserLocation(resultUsers.getDouble(3),
									resultUsers.getDouble(4));
							EdssSubscription sub = new EdssSubscription(resultUsers.getString(1),
									resultUsers.getString(7), resultUsers.getString(8), resultUsers.getString(9));
							User user = new User(resultUsers.getString(1), resultUsers.getString(2), location,
									resultUsers.getString(6));
							user.setSubscription(sub);

							double degreeChange = notification.getRange() * baseDegreeChange;
							double upperLatThreshold = cityMarker.getLatitude() + degreeChange;
							double lowerLatThreshold = cityMarker.getLatitude() - degreeChange;
							if (location.getLatitude() < upperLatThreshold
									&& location.getLatitude() > lowerLatThreshold) {
								double upperLonThreshold = cityMarker.getLongitude() + degreeChange;
								double lowerLonThreshold = cityMarker.getLongitude() - degreeChange;
								if (location.getLongitude() < upperLonThreshold
										&& location.getLongitude() > lowerLonThreshold) {
									users.add(user);
								}
							}
						}
					}
				}
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void saveUserResponse(UserResponse response) {
		String sqlStatement = "INSERT INTO " + Constants.USERRESPONSES_TABLE
				+ " (userid, city, disaster, state) VALUES (?, ?, ?, ?)";

		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

			preparedStatement.setString(1, response.getUserId());
			preparedStatement.setString(2, response.getCity());
			preparedStatement.setString(3, response.getDisaster());
			preparedStatement.setString(4, response.getState());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String exctactClosestCity(double userLatitude, double userLongitude) {
		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); Statement statement = connection.createStatement()) {

			String createTableQuery = "SELECT * FROM " + Constants.MARKERS_TABLE;

			ResultSet result = statement.executeQuery(createTableQuery);
			List<CityMarker> markers = new ArrayList<>();
			while (result.next()) {
				CityMarker marker = new CityMarker(result.getString(2), result.getDouble(3), result.getDouble(4));
				markers.add(marker);
			}
			CityMarker closest = markers.get(0);
			for (CityMarker marker : markers) {
				double closestLat = Math.abs(closest.getLatitude());
				double closestLon = Math.abs(closest.getLongitude());
				double markerLat = Math.abs(marker.getLatitude());
				double markerLon = Math.abs(marker.getLongitude());
				if (Math.abs(userLatitude - markerLat) < Math.abs(userLatitude - closestLat)) {
					if (Math.abs(userLongitude - markerLon) < Math.abs(userLongitude - closestLon)) {
						closest = marker;
					}
				}
			}
			return closest.getCityName();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String exctactClosestCity(String userId) {
		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); Statement statement = connection.createStatement()) {

			String createTableQuery = "SELECT * FROM " + Constants.USERS_TABLE + " WHERE USERID = '" + userId + "'";

			ResultSet result = statement.executeQuery(createTableQuery);
			User user = null;
			while (result.next()) {
				UserLocation location = new UserLocation(result.getDouble(3), result.getDouble(4));
				user = new User(result.getString(1), result.getString(2), location, result.getString(6));
			}
			return user.getClosestCity();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void updateDisasterResponseTable(String city, String disasterType, String disasterState) {
		String sqlStatement = "INSERT INTO " + Constants.DECISIONRESPONSES_TABLE
				+ " (city, disaster, state) VALUES (?, ?, ?)";

		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

			preparedStatement.setString(1, city);
			preparedStatement.setString(2, disasterType);
			preparedStatement.setString(3, disasterState);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<DecisionResponse> getDecisionResponses() {
		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); Statement statement = connection.createStatement()) {

			String createTableQuery = "SELECT * FROM " + Constants.DECISIONRESPONSES_TABLE;

			ResultSet result = statement.executeQuery(createTableQuery);
			List<DecisionResponse> responses = new ArrayList<>();
			while (result.next()) {
				DecisionResponse response = new DecisionResponse(result.getString(1), result.getString(2),
						result.getString(3), result.getString(4));
				responses.add(response);
			}
			return responses;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static CityMarker getNearestCityExit(double userLatitude, double userLongitude) {
		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); Statement statement = connection.createStatement()) {

			String createTableQuery = "SELECT * FROM " + Constants.EXITPOINTS_TABLE;

			ResultSet result = statement.executeQuery(createTableQuery);
			List<CityMarker> markers = new ArrayList<>();
			while (result.next()) {
				CityMarker marker = new CityMarker(result.getString(2), result.getDouble(3), result.getDouble(4));
				markers.add(marker);
			}
			CityMarker closest = markers.get(0);
			for (CityMarker marker : markers) {
				double closestLat = Math.abs(closest.getLatitude());
				double closestLon = Math.abs(closest.getLongitude());
				double markerLat = Math.abs(marker.getLatitude());
				double markerLon = Math.abs(marker.getLongitude());
				if (Math.abs(userLatitude - markerLat) < Math.abs(userLatitude - closestLat)) {
					if (Math.abs(userLongitude - markerLon) < Math.abs(userLongitude - closestLon)) {
						closest = marker;
					}
				}
			}
			return closest;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<SimulationDayData> getSimulationData() {
		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); Statement statement = connection.createStatement()) {

			String createTableQuery = "SELECT * FROM " + Constants.SIMULATION_TABLE;

			ResultSet result = statement.executeQuery(createTableQuery);
			List<SimulationDayData> simDays = new ArrayList<>();
			while (result.next()) {
				SimulationDayData simDay = new SimulationDayData(result.getInt(1), result.getInt(3), result.getInt(4),
						result.getInt(5), result.getInt(6), result.getInt(7), result.getInt(8), result.getInt(9),
						result.getInt(10), result.getFloat(11), result.getInt(12), result.getInt(13), result.getInt(15),
						result.getInt(16), result.getInt(17));
				simDays.add(simDay);
			}
			return simDays;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> getAllStoredDiagnostics() {
		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); Statement statement = connection.createStatement()) {

			String createTableQuery = "SELECT * FROM " + Constants.DIAGNOSTICS_TABLE;

			ResultSet result = statement.executeQuery(createTableQuery);
			List<String> diagnostics = new ArrayList<>();
			while (result.next()) {
				String dia = result.getString(3);
				diagnostics.add(dia);
			}
			return diagnostics;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void saveDiagnostic(String diagnostic) {
		String sqlStatement = "INSERT INTO " + Constants.DIAGNOSTICS_TABLE + " (name, total) VALUES (?, ?)";

		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

			preparedStatement.setString(1, HelperMethods.checkString(diagnostic));
			preparedStatement.setInt(2, 1);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void incrementDiagnosticNumber(String diagnostic, int numberOfDiagnostics) {
		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); Statement statement = connection.createStatement()) {

			String createTableQuery = "UPDATE " + Constants.DIAGNOSTICS_TABLE + " SET TOTAL = TOTAL + "
					+ String.valueOf(numberOfDiagnostics) + " WHERE NAME = '" + diagnostic + "'";

			statement.executeUpdate(createTableQuery);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static String getUserType(String userId) {
		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); Statement statement = connection.createStatement()) {

			String createTableQuery = "SELECT USERTYPE FROM " + Constants.USERS_TABLE + " WHERE USERID = '" + userId
					+ "'";

			ResultSet result = statement.executeQuery(createTableQuery);
			result.next();
			return result.getString(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> getAllStoredSymptoms(String diagnosticName) {
		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); Statement statement = connection.createStatement()) {

			String createTableQuery = "SELECT * FROM " + Constants.SYMPTOMS_TABLE + " WHERE DIAGNOSTICNAME = '"
					+ diagnosticName + "'";

			ResultSet result = statement.executeQuery(createTableQuery);
			List<String> symptoms = new ArrayList<>();
			while (result.next()) {
				String symptom = result.getString(2);
				symptoms.add(symptom);
			}
			return symptoms;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void saveSymptoms(String diagnostic, List<String> symptoms) {
		symptoms.forEach(symptom -> {
			String sqlStatement = "INSERT INTO " + Constants.SYMPTOMS_TABLE
					+ " (DIAGNOSTICNAME, SYMPTOM) VALUES (?, ?)";

			try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
					Constants.PASSWORD);
					PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

				preparedStatement.setString(1, diagnostic);
				preparedStatement.setString(2, symptom);

				preparedStatement.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		});
	}

	public static void updateLocation(String userId, Double latitude, Double longitude) {
		String query = "UPDATE " + Constants.USERS_TABLE + " SET LATITUDE=?, LONGITUDE=? WHERE USERID=?";
		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setDouble(1, latitude);
			preparedStatement.setDouble(2, longitude);
			preparedStatement.setString(3, userId);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addDiagnosticTimestamp(String diagnostic, int numberOfSickRegistered) {
		String sqlStatement = "INSERT INTO " + Constants.DIAGNOSTICS_TIMESTAMPS_TABLE
				+ " (diagnosticname, registered) VALUES (?, ?)";

		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

			preparedStatement.setString(1, diagnostic);
			preparedStatement.setInt(2, numberOfSickRegistered);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
