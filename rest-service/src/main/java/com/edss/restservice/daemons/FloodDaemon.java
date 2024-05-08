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
import com.edss.models.helperclasses.DisasterType;
import com.edss.models.helperclasses.HelperMethods;
import com.edss.restservice.NotificationService;

public class FloodDaemon implements Daemon {

	NotificationService notificationservice;
	List<UserResponse> floodResponsesOngoing = new ArrayList<UserResponse>();
	List<UserResponse> floodResponsesPossible = new ArrayList<UserResponse>();
	Map<String, Integer> cityAndResponsesOngoing = new HashMap<>();
	Map<String, Integer> cityAndResponsesPossible = new HashMap<>();

	public FloodDaemon(NotificationService notificationService) {
		this.notificationservice = notificationService;
	}

	@Override
	public void runDaemon() {
		try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
				Constants.PASSWORD); Statement statement = connection.createStatement()) {

			String createTableQuery = "SELECT * FROM " + Constants.USERRESPONSES_TABLE + " WHERE DISASTER = '"
					+ DisasterType.FLOOD.name() + "'";

			ResultSet result = statement.executeQuery(createTableQuery);
			List<UserResponse> responses = new ArrayList<>();
			while (result.next()) {
				UserResponse response = new UserResponse(result.getString(2), result.getString(3), result.getString(4),
						result.getString(5), result.getString(1));
				responses.add(response);
			}
			for (UserResponse response : responses) {
				if (response.getState().equals(Constants.DISASTER_STATE_ONGOING)) {
					this.floodResponsesOngoing.add(response);
				}
				if (response.getState().equals(Constants.DISASTER_STATE_POSSIBLE)) {
					this.floodResponsesPossible.add(response);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		checkForOngoing();
		sendOngoingAlert();
		checkForPossible();
		sendPossibleAlert();
	}

	@Override
	public void checkForOngoing() {
		floodResponsesOngoing.forEach(response -> {
			String city = response.getCity();
			if (!cityAndResponsesOngoing.containsKey(city)) {
				cityAndResponsesOngoing.put(city, 1);
			} else {
				Integer currentNumber = cityAndResponsesOngoing.get(city);
				cityAndResponsesOngoing.replace(city, currentNumber + 1);
			}

		});
	}

	@Override
	public void checkForPossible() {
		floodResponsesPossible.forEach(response -> {
			String city = response.getCity();
			if (!cityAndResponsesPossible.containsKey(city)) {
				cityAndResponsesPossible.put(city, 0);
			} else {
				Integer currentNumber = cityAndResponsesPossible.get(city);
				cityAndResponsesPossible.replace(city, currentNumber + 1);
			}

		});
	}

	@Override
	public void sendOngoingAlert() {
		cityAndResponsesOngoing.keySet().forEach(city -> {
			if (cityAndResponsesOngoing.get(city) >= Constants.SEND_ONGOING_ALERT_THRESHOLD) {

				MessageNotification notification = new MessageNotification(0, DisasterType.FLOOD.name(), city, "red",
						"fatal", 10, "ALERT! Possible " + DisasterType.FLOOD.name() + " in your area", 10.0 * 0.9);
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

				DbHelper.updateDisasterResponseTable(city, DisasterType.FLOOD.name(), Constants.DISASTER_STATE_ONGOING);

				floodResponsesOngoing.forEach(userResponse -> {
					if (userResponse.getCity() == city) {
						try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
								Constants.PASSWORD); Statement statement = connection.createStatement()) {

							String createTableQuery = "DELETE FROM " + Constants.USERRESPONSES_TABLE
									+ " WHERE USERID = '" + userResponse.getUserId() + "' and TIMESTAMP = '"
									+ userResponse.getTimestamp().getOriginalTimestamp() + "'";

							statement.execute(createTableQuery);
						} catch (SQLException e) {
							e.printStackTrace();
						}

					}
				});
			}
		});
		cityAndResponsesOngoing = new HashMap<String, Integer>();
		floodResponsesOngoing = new ArrayList<UserResponse>();
	}

	@Override
	public void sendPossibleAlert() {
		cityAndResponsesPossible.keySet().forEach(city -> {
			if (cityAndResponsesOngoing.get(city) >= Constants.SEND_POSSIBLE_ALERT_THRESHOLD) {

				DbHelper.updateDisasterResponseTable(city, DisasterType.FLOOD.name(),
						Constants.DISASTER_STATE_POSSIBLE);

				floodResponsesPossible.forEach(userResponse -> {
					if (userResponse.getCity() == city) {
						try (Connection connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.USERNAME,
								Constants.PASSWORD); Statement statement = connection.createStatement()) {

							String createTableQuery = "DELETE FROM " + Constants.USERRESPONSES_TABLE
									+ " WHERE USERID = '" + userResponse.getUserId() + "' and TIMESTAMP = '"
									+ userResponse.getTimestamp().getOriginalTimestamp() + "'";

							statement.executeQuery(createTableQuery);
						} catch (SQLException e) {
							e.printStackTrace();
						}

					}
				});
			}
		});
		cityAndResponsesPossible = new HashMap<String, Integer>();
		floodResponsesPossible = new ArrayList<UserResponse>();
	}

}
