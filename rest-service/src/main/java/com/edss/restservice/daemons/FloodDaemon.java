package com.edss.restservice.daemons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edss.models.EdssSubscription;
import com.edss.models.MessageNotification;
import com.edss.models.User;
import com.edss.models.UserResponse;
import com.edss.models.helperclasses.Constants;
import com.edss.models.helperclasses.DbHelper;
import com.edss.models.helperclasses.HelperMethods;
import com.edss.restservice.NotificationService;

public class FloodDaemon implements Daemon {

	NotificationService notificationservice;
	List<UserResponse> floodResponses;
	Map<String, Integer> cityAndResponses = new HashMap<>();

	public FloodDaemon(NotificationService notificationService) {
		this.notificationservice = notificationService;
	}

	@Override
	public void runDaemon() {
		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); Statement statement = connection.createStatement()) {

			String createTableQuery = "SELECT * FROM " + Constants.USERRESPONSES_TABLE + " WHERE DISASTER = '"
					+ Constants.DISASTER_TYPE_FLOOD + "'";

			ResultSet result = statement.executeQuery(createTableQuery);
			List<UserResponse> responses = new ArrayList<>();
			while (result.next()) {
				UserResponse response = new UserResponse(result.getString(2), result.getString(3), result.getString(4),
						result.getString(1));
				responses.add(response);
			}
			this.floodResponses = responses;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		checkForOngoing();
		sendOngoingAlert();
	}

	@Override
	public void checkForOngoing() {
		floodResponses.forEach(response -> {
			try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
					Constants.PASSWORD); Statement statement = connection.createStatement()) {

				String createTableQuery = "SELECT CLOSESTCITY FROM " + Constants.USERS_TABLE + " WHERE USERID = '"
						+ response.getUserId() + "'";

				ResultSet result = statement.executeQuery(createTableQuery);
				while (result.next()) {
					String city = result.getString(1);
					if (!cityAndResponses.containsKey(city)) {
						cityAndResponses.put(city, 0);
					} else {
						Integer currentNumber = cityAndResponses.get(city);
						cityAndResponses.replace(city, currentNumber + 1);
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		});
	}

	@Override
	public void checkForPossible() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendOngoingAlert() {
		cityAndResponses.keySet().forEach(city -> {
			if (cityAndResponses.get(city) >= Constants.SEND_ONGOING_ALERT_THRESHOLD) {

				MessageNotification notification = new MessageNotification(0, "FLOOD", city, "red", "fatal", 10,
						"ALERT! Possible FLOOD in your area", 10.0 * 0.9);
				List<User> users = DbHelper.getUsersInArea(notification);

				users.forEach(user -> {
					try {
						EdssSubscription sub = user.getSubscription();
						String payload = HelperMethods.constructPayload(notification);
						this.notificationservice.sendNotification(sub, payload);
						System.out.println("Notification sent to endpoint: " + sub.getEndpoint());
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
		});
		cityAndResponses = new HashMap<String, Integer>();
	}

	@Override
	public void sendPossibleAlert() {
		// TODO Auto-generated method stub

	}

}
