package com.edss.models.helperclasses;

import java.util.List;
import java.util.Map;

public class Constants {

	public static final String JDBC_URL = "jdbc:mysql://localhost:3306/edss";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "1234";

	public static final String ID_AUTO_INCREMENT = "id INT AUTO_INCREMENT PRIMARY KEY,";
	public static final String TIMESTAMP_CURRENT = "TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP,";
	public static final String MARKERS_TABLE = "MARKERS";
	public static final String NOTIFICATIONS_TABLE = "NOTIFICATIONS";
	public static final String USERS_TABLE = "USERS";
	public static final String SUBSCRIPTION_TABLE = "SUBSCRIPTION";
	public static final String USERRESPONSES_TABLE = "USERRESPONSES";
	public static final String DECISIONRESPONSES_TABLE = "DECISIONRESPONSES";

	public static Map<String, List<Double>> cities = HelperMethods.initCities();

	public static final String FLOOD_PRESENT_QUESTION = "Is there any flood danger?";
	public static final String FLOOD_ONGOING_QUESTION = "Is the flood ongoing?";

	public static final String RESPONSE_YES = "Yes";
	public static final String RESPONSE_NO = "No";

	public static final String DISASTER_TYPE_FLOOD = "FLOOD";

	public static final String DISASTER_STATE_POSSIBLE = "POSSIBLE";
	public static final String DISASTER_STATE_ONGOING = "ONGOING";
	public static final String DISASTER_STATE_NONEXISTENT = "NONEXISTENT";

	public static final int SEND_ONGOING_ALERT_THRESHOLD = 1;
	public static final int SEND_POSSIBLE_ALERT_THRESHOLD = 1;

}
