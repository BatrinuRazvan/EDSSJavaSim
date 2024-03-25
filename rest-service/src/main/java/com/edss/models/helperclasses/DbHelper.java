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
import com.edss.models.EdssSubscription;
import com.edss.models.MessageNotification;
import com.edss.models.User;
import com.edss.models.UserLocation;

public class DbHelper {

	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/edss";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "1234";
	private static Double baseDegreeChange = 0.09;

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
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
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
				+ " (id, email, latitude, longitude) VALUES (?, ?, ?, ?)";

		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

			preparedStatement.setString(1, newUser.getUserId());
			preparedStatement.setString(2, newUser.getEmail());
			preparedStatement.setDouble(3, newUser.getCurrentLocation().getLatitude());
			preparedStatement.setDouble(4, newUser.getCurrentLocation().getLongitude());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<EdssSubscription> getAllUsers() {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			String createTableQuery = "SELECT * FROM " + Constants.USERS_TABLE;

			ResultSet result = statement.executeQuery(createTableQuery);
			List<User> users = new ArrayList<>();
			while (result.next()) {
				User user = new User(result.getString(1), result.getString(2),
						new UserLocation(result.getDouble(3), result.getDouble(4)));
				users.add(user);
			}
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void saveSubscription(EdssSubscription subscription) {
		// Assuming Constants.SUBSCRIPTION_TABLE holds the name of your table
		// and you have columns named `endpoint`, `p256dh`, `auth`, and `user_id` (or
		// similar) in your table
		String sqlStatement = "UPDATE " + Constants.USERS_TABLE + " SET endpoint = ?, p256 = ?, auth = ? WHERE id = ?";

		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

			// Set the subscription details in the prepared statement
			preparedStatement.setString(1, subscription.getEndpoint());
			preparedStatement.setString(2, subscription.getP256dh());
			preparedStatement.setString(3, subscription.getAuth());
			preparedStatement.setString(4, subscription.getUserId()); // Assuming the user ID is the last parameter

			// Execute the update
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
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

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
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
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
									resultUsers.getString(5), resultUsers.getString(6), resultUsers.getString(7));
							User user = new User(resultUsers.getString(1), resultUsers.getString(2), location);
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

}
